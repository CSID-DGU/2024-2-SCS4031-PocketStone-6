package com.pocketstone.team_sync.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.pocketstone.team_sync.dto.teammatedto.AddTeammateRequestDto;
import com.pocketstone.team_sync.dto.teammatedto.RecommendationRequestDto;
import com.pocketstone.team_sync.dto.teammatedto.RecommendationResponseDto;
import com.pocketstone.team_sync.dto.teammatedto.TeamListResponseDto;
import com.pocketstone.team_sync.entity.Company;
import com.pocketstone.team_sync.entity.Employee;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.ProjectTeammate;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.repository.CompanyRepository;
import com.pocketstone.team_sync.repository.EmployeeRepository;
import com.pocketstone.team_sync.repository.ProjectRepository;
import com.pocketstone.team_sync.repository.ProjectTeammateRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ProjectTeammateService {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectTeammateRepository teammateRepository;
    private final ProjectRepository projectRepository;

    // 외부 api 요청
    private final WebClient webClient_model;

    //팀원 추천 요청
    public RecommendationResponseDto recommendTeammate(User user, RecommendationRequestDto body){
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        if (company == null){
            return null;
        }
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
    public void registerTeammate(User user, AddTeammateRequestDto request) {
        try {
            Company company  = companyRepository.findByUserId(user.getId())
                                                    .orElseThrow(() -> new RuntimeException("Company not found"));
            //사용자 확인
            Project project = projectRepository.findByUserAndId(user, request.getProjectId())
                                       .orElseThrow(() -> new RuntimeException("Project not found"));

            List<ProjectTeammate> teammateList = new ArrayList<>();
            for (Long employeeId : request.getEmployeeIds()) {
                Employee employee = employeeRepository.findByCompanyAndId(company, employeeId)
                                            .orElseThrow(() -> new RuntimeException("Employee not found")); // 사원 확인
                teammateList.add(new ProjectTeammate(project,employee));
            }
            teammateRepository.saveAll(teammateList);//저장
        } catch (Exception e) {
            System.err.println("에러발생");
        }   
    }

    //팀원 조회
    public List<TeamListResponseDto> getTeammate(User user, Long projectId) {
        Company company  = companyRepository.findByUserId(user.getId())
                                                    .orElseThrow(() -> new RuntimeException("Company not found"));
        Project project = projectRepository.findByUserAndId(user, projectId)
                                                    .orElseThrow(() -> new RuntimeException("Project not found"));
        List<ProjectTeammate> teamList = teammateRepository.findByProject(project);
        List<TeamListResponseDto> responseList = new ArrayList<>();
        for (int i=0; i < teamList.size(); i++) {
            responseList.add(new TeamListResponseDto(teamList.get(i).getEmployee().getId(),teamList.get(i).getEmployee().getPosition()));
        }

        return responseList;
    }

}
