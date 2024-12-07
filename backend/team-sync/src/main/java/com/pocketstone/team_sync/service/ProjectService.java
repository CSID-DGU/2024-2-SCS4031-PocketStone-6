package com.pocketstone.team_sync.service;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.pocketstone.team_sync.entity.*;
import com.pocketstone.team_sync.repository.ProjectCharterRepository;
import com.pocketstone.team_sync.repository.TimelineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pocketstone.team_sync.dto.projectdto.ProjectDto;
import com.pocketstone.team_sync.entity.enums.ProjectStatus;
import com.pocketstone.team_sync.exception.ProjectNotFoundException;
import com.pocketstone.team_sync.repository.CompanyRepository;
import com.pocketstone.team_sync.repository.ProjectRepository;
import com.pocketstone.team_sync.utility.ProjectValidationUtils;


import lombok.RequiredArgsConstructor;


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


    public ProjectDto save(User user, ProjectDto dto){
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        Project project = projectRepository.save(Project.builder()
                .projectName(dto.getProjectName())
                .startDate(dto.getStartDate())
                .mvpDate(dto.getMvpDate())
                .company(company)
                .build());
        return ProjectDto.toProjectDto(project);
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