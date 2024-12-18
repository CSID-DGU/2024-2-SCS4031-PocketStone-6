package com.pocketstone.team_sync.service;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.pocketstone.team_sync.dto.MessageResponseDto;
import com.pocketstone.team_sync.dto.projectdto.ProjectDto;
import com.pocketstone.team_sync.entity.Company;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.ProjectCharter;
import com.pocketstone.team_sync.entity.Timeline;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.entity.enums.ProjectStatus;
import com.pocketstone.team_sync.event.ProjectRegisteredEvent;
import com.pocketstone.team_sync.exception.ProjectNotFoundException;
import com.pocketstone.team_sync.repository.CompanyRepository;
import com.pocketstone.team_sync.repository.ProjectCharterRepository;
import com.pocketstone.team_sync.repository.ProjectRepository;
import com.pocketstone.team_sync.repository.TimelineRepository;
import com.pocketstone.team_sync.utility.ProjectValidationUtils;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {

    private final CompanyRepository companyRepository;
    private final ProjectRepository projectRepository;
    private final ProjectCharterRepository  projectCharterRepository;
    private final TimelineRepository timelineRepository;

    private final TimelineService timelineService;
    private final ProjectCharterService projectCharterService;

    private final EnumMap<ProjectStatus, Function<User, List<ProjectDto>>> statusToFunctionMap = new EnumMap<>(ProjectStatus.class);
    {
        statusToFunctionMap.put(ProjectStatus.ONGOING, this::findOngoingProjects);
        statusToFunctionMap.put(ProjectStatus.COMPLETED, this::findCompletedProjects);
        statusToFunctionMap.put(ProjectStatus.ALL, this::findAllProjects);}
    
    private final WebClient webClient_model;
    private final ApplicationEventPublisher eventPublisher;

     // 모델서버에 요청보내기
    private Mono<MessageResponseDto> requestEmbeddingProject(String path, Long companyId, Long projectId){
        return webClient_model.post()
            .uri(uriBuilder -> uriBuilder
                .path(path)
                .queryParam("company_id", companyId)  // company_id를 URL 파라미터로 추가
                .queryParam("project_id", projectId)  // project_id를 URL 파라미터로 추가
                .build())
            //.bodyValue()
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
            .bodyToMono(MessageResponseDto.class); // 응답을 DTO로 처리
    }

    public ProjectDto save(User user, ProjectDto dto){
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        Project project = projectRepository.save(Project.builder()
                .projectName(dto.getProjectName())
                .startDate(dto.getStartDate())
                .mvpDate(dto.getMvpDate())
                .company(company)
                .build());
        
        // 이벤트 발행
        eventPublisher.publishEvent(new ProjectRegisteredEvent(company.getId(), project.getId()));
        return ProjectDto.toProjectDto(project);
    }

    @Async
    @EventListener
    public void handleProjectRegisteredEvent(ProjectRegisteredEvent event) {
        Long companyId = event.getCompanyId();
        Long projectId = event.getProjectId();

        requestEmbeddingProject("/api/embedding-project/", companyId, projectId)
        .doOnError(e -> {
            throw new RuntimeException("프로젝트 정보 임베딩 실패: " + e.getMessage());
        })
        .subscribe(); // 비동기 실행
    }

    //이름으로 프로젝트 찾기, dto로 변환
    public ProjectDto findByProjectName(User user, String projectName){
        Company company  = companyRepository.findByUserId(user.getId())
                                                .orElseThrow(() -> new RuntimeException("Company not found"));
        return projectRepository.findByProjectNameAndCompany(projectName, company)
                .map(project -> new ProjectDto(
                        project.getId(),
                        project.getProjectName(),
                        project.getStartDate(),
                        project.getMvpDate()
                ))
                .orElseThrow(() -> new ProjectNotFoundException(projectName));
    }

    public ProjectDto findByProjectId(User user, Long projectId){
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        Optional<Project> project = projectRepository.findById(projectId);
        if(project.isEmpty()) throw new ProjectNotFoundException("");
        ProjectValidationUtils.validateProjectOwner(company, project.get());
        return ProjectDto.toProjectDto(project.get());
    }





    public List<ProjectDto> getProjectsByStatus(ProjectStatus status, User user) {
        return statusToFunctionMap.getOrDefault(status, this::findAllProjects).apply(user);
    }


    public List<ProjectDto> findOngoingProjects(User user){
        Company company  = companyRepository.findByUserId(user.getId())
                                                .orElseThrow(() -> new RuntimeException("Company not found"));
        List<Project> projects = projectRepository.findAllByCompany(company);
        return projects.stream()
                .filter(project -> project.getMvpDate().isAfter(LocalDate.now()))
                .map(project -> new ProjectDto(
                        project.getId(),
                        project.getProjectName(),
                        project.getStartDate(),
                        project.getMvpDate()
                ))
                .collect(Collectors.toList());
    }

    public List<ProjectDto> findCompletedProjects(User user){
        Company company  = companyRepository.findByUserId(user.getId())
                                                .orElseThrow(() -> new RuntimeException("Company not found"));
        List<Project> projects = projectRepository.findAllByCompany(company);
        return projects.stream()
                .filter(project -> project.getMvpDate().isBefore(LocalDate.now()))
                .map(project -> new ProjectDto(
                        project.getId(),
                        project.getProjectName(),
                        project.getStartDate(),
                        project.getMvpDate()
                ))
                .collect(Collectors.toList());
    }

    //모든 프로젝트 찾기, dto로 변환
    public List<ProjectDto> findAllProjects(User user) {
        Company company  = companyRepository.findByUserId(user.getId())
                                                .orElseThrow(() -> new RuntimeException("Company not found"));
        List<Project> projects = projectRepository.findAllByCompany(company); // 모든 프로젝트 엔티티

        // 프로젝트 엔티티 dto 로 변환
        return projects.stream()
                .map(project -> new ProjectDto(
                        project.getId(),
                        project.getProjectName(),
                        project.getStartDate(),
                        project.getMvpDate()
                ))
                .collect(Collectors.toList()); // 모든 결과값들 dto리스트로 콜렉트
    }

    public void deleteProject(User user, Long projectId) {
        Optional<Project> project = projectRepository.findById(projectId);
        if(project.isEmpty()) throw new ProjectNotFoundException("");

        ProjectValidationUtils.validateProjectOwner(user.getCompany(), project.get());

        List<Timeline> timelines = timelineRepository.findAllByProjectId(projectId);
        if(!timelines.isEmpty()){
            timelineService.deleteTimelines(user, projectId);

        }

        Optional<ProjectCharter> charter = projectCharterRepository.findByProjectId(projectId);
        if (charter.isPresent()){
            projectCharterService.deleteProjectCharter(user, projectId);
        }

        projectRepository.delete(project.get());
    }
}