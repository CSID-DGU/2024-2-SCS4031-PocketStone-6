package com.pocketstone.team_sync.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.pocketstone.team_sync.dto.teammatedto.RecommendationRequestDto;
import com.pocketstone.team_sync.dto.teammatedto.RecommendationResponseDto;
import com.pocketstone.team_sync.entity.Company;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.repository.CompanyRepository;
import com.pocketstone.team_sync.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ProjectTeammateService {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    // 외부 api 요청
    private final WebClient webClient_model;

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

    private Mono<RecommendationResponseDto> requestRecommendation(RecommendationRequestDto body){
        //RecommendationRequestDto newbody = new RecommendationRequestDto(body.getProjectId(),body.getMember());
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

}
