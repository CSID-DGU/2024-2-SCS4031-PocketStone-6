package com.pocketstone.team_sync.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.pocketstone.team_sync.exception.AllocationNotAvailableException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.pocketstone.team_sync.dto.memberdto.MemberListResponseDto;
import com.pocketstone.team_sync.dto.memberdto.MemberRequestDto;
import com.pocketstone.team_sync.dto.memberdto.RecommendationRequestDto;
import com.pocketstone.team_sync.dto.memberdto.RecommendationResponseDto;
import com.pocketstone.team_sync.entity.Company;
import com.pocketstone.team_sync.entity.Employee;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.ProjectMember;
import com.pocketstone.team_sync.entity.Timeline;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.exception.ExceededWorkloadException;
import com.pocketstone.team_sync.exception.ProjectNotFoundException;
import com.pocketstone.team_sync.repository.CompanyRepository;
import com.pocketstone.team_sync.repository.EmployeeRepository;
import com.pocketstone.team_sync.repository.ManMonthRepository;
import com.pocketstone.team_sync.repository.ProjectMemberRepository;
import com.pocketstone.team_sync.repository.ProjectRepository;
import com.pocketstone.team_sync.repository.TimelineRepository;

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
    private final ManMonthRepository manMonthRepository;

    private final ManMonthService manMonthService;
    // 외부 api 요청
    private final WebClient webClient_model;

    //팀원 추천 요청
    public RecommendationResponseDto recommendMember(User user, Long projectId){
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        if (company == null){
            return null;
        }
        RecommendationRequestDto request  = new RecommendationRequestDto();

        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(""));
        List<Timeline> timelines = timelineRepository.findAllByProjectId(projectId);
        Map<LocalDate, Double> requiredManmonths = buildManmonthMapFromTimelines(timelines);

        List<Employee> availableEmployees = employeeRepository.findByCompany(company).stream()
                .filter(employee -> {
                    System.out.println("\nChecking employee: " + employee.getId());
                    Map<LocalDate, Double> availability = manMonthService.checkAvailability(
                            employee,
                            project.getStartDate(),
                            project.getMvpDate()
                    );

                    boolean isAvailable = requiredManmonths.entrySet().stream()
                            .allMatch(entry -> {
                                LocalDate date = entry.getKey();
                                Double required = entry.getValue();
                                Double available = availability.getOrDefault(entry.getKey(), 0.25);
                                System.out.println("Date: " + date +
                                        ", Required: " + required +
                                        ", Available: " + available +
                                        ", Is Available: " + (available >= required));
                                return available >= required;
                            });
                    System.out.println("Final availability for employee " + employee.getId() + ": " + isAvailable);
                    return isAvailable;
                })
                .toList();

        request.setProjectId(projectId);
        request.setAvailableEmployeeIds(availableEmployees.stream()
                .map(Employee::getId)
                .toList());
        System.out.println("Total employees before filtering: " + employeeRepository.findByCompany(company).size());
        System.out.println("Available employees after filtering: " + availableEmployees.size());
        System.out.println("Available employee IDs: " + availableEmployees.stream().map(Employee::getId).toList());
        try {
            return requestRecommendation(request,company.getId())//추천 요청
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("추천 실패");
        }
    }
    // 모델서버에 요청보내기
    private Mono<RecommendationResponseDto> requestRecommendation(RecommendationRequestDto body,Long companyId){
        return webClient_model.post()
            .uri(uriBuilder -> uriBuilder
                .path("/api/recommendation/")
                .queryParam("company_id", companyId)  // company_id를 URL 파라미터로 추가
                .build())
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
    public void registerMember(User user, MemberRequestDto request, Long projectId) {
        
        Company company  = companyRepository.findByUserId(user.getId())
                                                .orElseThrow(() -> new RuntimeException("Company not found"));
        //회사에 해당 프로젝트 속하는지 확인
        Project project = projectRepository.findByCompanyAndId(company, projectId)
                .orElseThrow(() -> new ProjectNotFoundException(""));
        deleteAllMembers(user,projectId);
        manMonthRepository.flush();
        memberRepository.flush();
        
        List<Timeline> timelines = timelineRepository.findAllByProjectId(projectId);
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
    public void deleteMember(User user, MemberRequestDto request, Long projectId) {
    
        Company company  = companyRepository.findByUserId(user.getId())
                                                .orElseThrow(() -> new RuntimeException("Company not found"));
        //회사 확인
        if (!projectRepository.existsByCompanyIdAndId(company.getId(), projectId)) {//회사에 해당 프로젝트있는지(company로 변경해야함)
            throw new ProjectNotFoundException("");
        }
        
        for (Long employeeId : request.getEmployeeIds()) {
            Employee employee = employeeRepository.findByCompanyAndId(company, employeeId)
                                        .orElseThrow(() -> new RuntimeException("Employee not found")); // 사원 확인
            ProjectMember member = memberRepository.findByProjectIdAndEmployeeId(projectId,employeeId)
                                        .orElseThrow(() -> new RuntimeException("member not found"));//해당 프로젝트 소속인지
            manMonthRepository.deleteAllByEmployeeIdAndProjectId(employeeId, projectId);
            memberRepository.deleteById(member.getId());
        }
        
    }

    //팀원 전체 삭제
    @Transactional
    public void deleteAllMembers(User user, Long projectId) {
    
        Company company  = companyRepository.findByUserId(user.getId())
                                                .orElseThrow(() -> new RuntimeException("Company not found"));
        //회사 확인
        if (!projectRepository.existsByCompanyIdAndId(company.getId(), projectId)) {//회사에 해당 프로젝트있는지(company로 변경해야함)
            throw new ProjectNotFoundException("");
        }
        
        if (memberRepository.existsByProjectId(projectId)){
            manMonthRepository.deleteAllByProjectId(projectId);
            memberRepository.deleteAllByProjectId(projectId); 
        }
         
    }

    //팀원 조회
    public List<MemberListResponseDto> getMember(User user, Long projectId) {
        Company company = companyRepository.findByUserId(user.getId())
                                                    .orElseThrow(() -> new RuntimeException("Company not found"));
        if (!projectRepository.existsByCompanyIdAndId(company.getId(), projectId)) {//회사에 해당 프로젝트있는지(company로 변경해야함함)
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
            System.out.println("\n타임라인: " + timeline.getId());
            System.out.println("Sprint Content: " + timeline.getSprintContent());
            System.out.println("Start Date: " + timeline.getSprintStartDate());
            System.out.println("End Date: " + timeline.getSprintEndDate());
            System.out.println("총 필요 맨먼스: " + timeline.getRequiredManmonth());

            LocalDate currentDate = timeline.getSprintStartDate();

            long totalWeeks = ChronoUnit.WEEKS.between(
                    timeline.getSprintStartDate(),
                    timeline.getSprintEndDate().plusDays(1)
            );

            System.out.println("스프린트 총 주 수: " + totalWeeks);


            double weeklyManMonth = timeline.getRequiredManmonth() / (double) totalWeeks;

            System.out.println("주 단위 필요 맨먼스: " + weeklyManMonth);


            while (!currentDate.isAfter(timeline.getSprintEndDate())) {
                manmonths.merge(
                        currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)),
                        weeklyManMonth,
                        Double::sum
                );

                currentDate = currentDate.plusWeeks(1);
            }
        }
        System.out.println("\n타임라인 manmonth values:");
        manmonths.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                });

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
