package com.pocketstone.team_sync.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.pocketstone.team_sync.entity.*;
import com.pocketstone.team_sync.exception.ExceededWorkloadException;
import com.pocketstone.team_sync.exception.ProjectNotFoundException;
import com.pocketstone.team_sync.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.pocketstone.team_sync.dto.memberdto.MemberListResponseDto;
import com.pocketstone.team_sync.dto.memberdto.MemberRequestDto;
import com.pocketstone.team_sync.dto.memberdto.RecommendationRequestDto;
import com.pocketstone.team_sync.dto.memberdto.RecommendationResponseDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ProjectMemberService {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectMemberRepository memberRepository;
    private final ProjectRepository projectRepository;
    private final TimelineRepository timelineRepository;

    private final ManMonthService manMonthService;
    // 외부 api 요청
    private final WebClient webClient_model;

    //팀원 추천 요청
    public RecommendationResponseDto recommendMember(User user, RecommendationRequestDto body){
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        if (company == null){
            return null;
        }
        Project project = projectRepository.findById(body.getProjectId()).orElseThrow(() -> new ProjectNotFoundException(""));
        List<Timeline> timelines = timelineRepository.findAllByProjectId(body.getProjectId());
        Map<LocalDate, Double> requiredManmonths = buildManmonthMapFromTimelines(timelines);

        List<Employee> availableEmployees = employeeRepository.findByCompany(company).stream()
                .filter(employee -> {
                    Map<LocalDate, Double> availability = manMonthService.checkAvailability(
                            employee,
                            project.getStartDate(),
                            project.getMvpDate()
                    );

                    return requiredManmonths.entrySet().stream()
                            .allMatch(entry -> {
                                Double required = entry.getValue();
                                Double available = availability.getOrDefault(entry.getKey(), 0.25);
                                return available >= required;
                            });
                })
                .toList();

        body.setAvailableEmployeeIds(availableEmployees.stream()
                .map(Employee::getId)
                .toList());
        try {
            return requestRecommendation(body)//추천 요청
                    .block();
        } catch (Exception e) {
            System.err.println("에러발생");
            return null;
        }
    }
    // 모델서버에 요청보내기
    private Mono<RecommendationResponseDto> requestRecommendation(RecommendationRequestDto body){
        return webClient_model.post()
            .uri("/api/recommendation") //endpoint설정
            .bodyValue(body)
            .retrieve()
            .onStatus(//클라이언트 에러
                status -> status.is4xxClientError(),
                clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(errorResponse -> {
                            // 클라이언트 오류 처리 (4xx)
                            System.err.println("에러발생222");
                            return Mono.error(new RuntimeException("Client error: " + errorResponse));
                        })
            )
            .onStatus( //서버에러
                status -> status.is5xxServerError(),
                    clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(errorResponse -> {
                            // 서버 오류 처리 (5xx)
                            System.err.println("에러발생222666666");
                            return Mono.error(new RuntimeException("Server error: " + errorResponse));
                        })
            )
            .bodyToMono(RecommendationResponseDto.class); // 응답을 DTO로 처리
    }

    //팀원 저장
    @Transactional
    public void registerMember(User user, MemberRequestDto request) {
        
        Company company  = companyRepository.findByUserId(user.getId())
                                                .orElseThrow(() -> new RuntimeException("Company not found"));
        //회사에 해당 프로젝트 속하는지 확인
        Project project = projectRepository.findByUserAndId(user, request.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException(""));

        List<Timeline> timelines = timelineRepository.findAllByProjectId(request.getProjectId());
        Map<LocalDate, Double> projectManmonths = buildManmonthMapFromTimelines(timelines);
        
        List<ProjectMember> memberList = new ArrayList<>();
        for (Long employeeId : request.getEmployeeIds()) {
            Employee employee = employeeRepository.findByCompanyAndId(company, employeeId)
                                        .orElseThrow(() -> new RuntimeException("Employee not found")); // 사원 확인

            if (!checkEmployeeAvailability(employee, project, projectManmonths)) {
                throw new ExceededWorkloadException(employee.getName(), 0.0);
            }

            for (Timeline timeline : timelines) {
                Map<LocalDate, Double> timelineManmonths = extractManmonthsForTimeline(
                        projectManmonths,
                        timeline.getSprintStartDate(),
                        timeline.getSprintEndDate()
                );

                manMonthService.allocateManmonth(
                        user,
                        employee,
                        project,
                        timeline,
                        timeline.getSprintStartDate(),
                        timeline.getSprintEndDate(),
                        timelineManmonths
                );
            }
            memberList.add(new ProjectMember(project,employee));
        }
        memberRepository.saveAll(memberList);//저장
          
    }

    //팀원 삭제
    @Transactional
    public void deleteMember(User user, MemberRequestDto request) {
    
        Company company  = companyRepository.findByUserId(user.getId())
                                                .orElseThrow(() -> new RuntimeException("Company not found"));
        //회사 확인
        if (!projectRepository.existsByUserIdAndId(user.getId(), request.getProjectId())) {//회사에 해당 프로젝트있는지(company로 변경해야함)
            throw new RuntimeException("Project not found");
        }
        
        for (Long employeeId : request.getEmployeeIds()) {
            Employee employee = employeeRepository.findByCompanyAndId(company, employeeId)
                                        .orElseThrow(() -> new RuntimeException("Employee not found")); // 사원 확인
            ProjectMember member = memberRepository.findByProjectIdAndEmployeeId(request.getProjectId(),employeeId)
                                        .orElseThrow(() -> new RuntimeException("member not found"));//해당 프로젝트 소속인지
            
            memberRepository.deleteById(member.getId());
        }
        
    }

    //팀원 전체 삭제
    @Transactional
    public void deleteAllMembers(User user, Long projectId) {
    
        Company company  = companyRepository.findByUserId(user.getId())
                                                .orElseThrow(() -> new RuntimeException("Company not found"));
        //회사 확인
        if (!projectRepository.existsByUserIdAndId(user.getId(), projectId)) {//회사에 해당 프로젝트있는지(company로 변경해야함)
            throw new RuntimeException("Project not found");
        }
        
        if (!memberRepository.existsByProjectId(projectId)){
            throw new RuntimeException("Any member not found");
        }
            
        memberRepository.deleteAllByProjectId(projectId);  
    }

    //팀원 조회
    public List<MemberListResponseDto> getMember(User user, Long projectId) {
        Company company = companyRepository.findByUserId(user.getId())
                                                    .orElseThrow(() -> new RuntimeException("Company not found"));
        if (!projectRepository.existsByUserIdAndId(user.getId(), projectId)) {//회사에 해당 프로젝트있는지(company로 변경해야함함)
            throw new RuntimeException("Project not found");
        }
        List<ProjectMember> memberList = memberRepository.findByProjectId(projectId);
        List<MemberListResponseDto> responseList = new ArrayList<>();
        for (int i=0; i < memberList.size(); i++) {
            responseList.add(new MemberListResponseDto(memberList.get(i).getEmployee().getId(),memberList.get(i).getEmployee().getPosition()));
        }

        return responseList;
    }

    private boolean checkEmployeeAvailability(Employee employee, Project project, Map<LocalDate, Double> requiredManmonths){
        Map<LocalDate, Double> availability = manMonthService.checkAvailability(
                employee,
                project.getStartDate(),
                project.getMvpDate()
        );
        return requiredManmonths.entrySet().stream()
                .allMatch(entry -> {
                    Double required = entry.getValue();
                    Double available = availability.getOrDefault(entry.getKey(), 0.25);
                    return required <= available;
                });
    }

    private Map<LocalDate, Double> buildManmonthMapFromTimelines(List<Timeline> timelines) {
        Map<LocalDate, Double> manmonths = new HashMap<>();

        for (Timeline timeline : timelines) {
            LocalDate currentDate = timeline.getSprintStartDate();
            while (!currentDate.isAfter(timeline.getSprintEndDate())) {

                manmonths.merge(
                        currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)),
                        timeline.getRequiredManmonth(),
                        Double::sum
                );
                currentDate = currentDate.plusWeeks(1);
            }
        }

        return manmonths;
    }

    private Map<LocalDate, Double> extractManmonthsForTimeline(
            Map<LocalDate, Double> projectManmonths,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return projectManmonths.entrySet().stream()
                .filter(entry -> !entry.getKey().isBefore(startDate) && !entry.getKey().isAfter(endDate))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

}
