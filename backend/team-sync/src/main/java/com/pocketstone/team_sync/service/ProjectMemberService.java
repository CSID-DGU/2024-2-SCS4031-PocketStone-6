package com.pocketstone.team_sync.service;

import java.util.ArrayList;
import java.util.List;

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
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.repository.CompanyRepository;
import com.pocketstone.team_sync.repository.EmployeeRepository;
import com.pocketstone.team_sync.repository.ProjectMemberRepository;
import com.pocketstone.team_sync.repository.ProjectRepository;

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

    // 외부 api 요청
    private final WebClient webClient_model;

    //팀원 추천 요청
    public RecommendationResponseDto recommendMember(User user, RecommendationRequestDto body){
        Company company  = companyRepository.findByUserId(user.getId())
                                                .orElseThrow(() -> new RuntimeException("Company not found"));
        //회사에 해당 프로젝트 속하는지 확인
        Project project = projectRepository.findByUserAndId(user, body.getProjectId())
                                    .orElseThrow(() -> new RuntimeException("Project not found"));
        
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
                                    .orElseThrow(() -> new RuntimeException("Project not found"));
        
        List<ProjectMember> memberList = new ArrayList<>();
        for (Long employeeId : request.getEmployeeIds()) {
            Employee employee = employeeRepository.findByCompanyAndId(company, employeeId)
                                        .orElseThrow(() -> new RuntimeException("Employee not found")); // 사원 확인
            memberList.add(new ProjectMember(project,employee));
        }
         // 기존 프로젝트의 모든 팀원 삭제
        memberRepository.deleteAllByProjectId(request.getProjectId());  
        memberRepository.flush(); // 데이터베이스와 동기화
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
            responseList.add(new MemberListResponseDto(memberList.get(i).getEmployee().getId()));
        }

        return responseList;
    }

}
