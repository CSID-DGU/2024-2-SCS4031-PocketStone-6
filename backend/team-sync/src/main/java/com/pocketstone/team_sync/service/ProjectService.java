package com.pocketstone.team_sync.service;

import com.pocketstone.team_sync.dto.projectdto.ProjectDto;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.entity.enums.ProjectStatus;
import com.pocketstone.team_sync.exception.ProjectNotFoundException;
import com.pocketstone.team_sync.exception.UnauthorizedAccessException;
import com.pocketstone.team_sync.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final EnumMap<ProjectStatus, Function<User, List<ProjectDto>>> statusToFunctionMap = new EnumMap<>(ProjectStatus.class);
    {   statusToFunctionMap.put(ProjectStatus.UPCOMING, this::findUpcomingProjects);
        statusToFunctionMap.put(ProjectStatus.ONGOING, this::findOngoingProjects);
        statusToFunctionMap.put(ProjectStatus.COMPLETED, this::findCompletedProjects);
        statusToFunctionMap.put(ProjectStatus.ALL, this::findAllProjects);}



    public ProjectDto save(User user, ProjectDto dto){
        Project project = projectRepository.save(Project.builder()
                .projectName(dto.getProjectName())
                .startDate(dto.getStartDate())
                .mvpDate(dto.getMvpDate())
                .user(user)
                .build());
        return ProjectDto.toProjectDto(project);
    }

    //이름으로 프로젝트 찾기, dto로 변환
    public ProjectDto findByProjectName(User user, String projectName){
        return projectRepository.findByProjectNameAndUser(projectName, user)
                .map(project -> new ProjectDto(
                        project.getId(),
                        project.getProjectName(),
                        project.getStartDate(),
                        project.getMvpDate()
                ))
                .orElseThrow(() -> new ProjectNotFoundException(projectName));
    }





    public List<ProjectDto> getProjectsByStatus(ProjectStatus status, User user) {
        return statusToFunctionMap.getOrDefault(status, this::findAllProjects).apply(user);
    }

    public List<ProjectDto> findUpcomingProjects(User user){
        List<Project> projects = projectRepository.findAllByUser(user);
        return projects.stream()
                .filter(project -> project.getStartDate().isAfter(LocalDate.now()))
                .map(project -> new ProjectDto(
                        project.getId(),
                        project.getProjectName(),
                        project.getStartDate(),
                        project.getMvpDate()
                ))
                .collect(Collectors.toList());
    }

    public List<ProjectDto> findOngoingProjects(User user){
        List<Project> projects = projectRepository.findAllByUser(user);
        return projects.stream()
                .filter(project -> project.getStartDate().isBefore(LocalDate.now()) && project.getMvpDate().isAfter(LocalDate.now()))
                .map(project -> new ProjectDto(
                        project.getId(),
                        project.getProjectName(),
                        project.getStartDate(),
                        project.getMvpDate()
                ))
                .collect(Collectors.toList());
    }

    public List<ProjectDto> findCompletedProjects(User user){
        List<Project> projects = projectRepository.findAllByUser(user);
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
        List<Project> projects = projectRepository.findAllByUser(user); // 모든 프로젝트 엔티티

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
}