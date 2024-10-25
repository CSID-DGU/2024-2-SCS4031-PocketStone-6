package com.pocketstone.team_sync.service;

import com.pocketstone.team_sync.dto.projectdto.ProjectDto;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectDto save(ProjectDto dto){
        projectRepository.save(Project.builder()
                .projectName(dto.getProjectName())
                .startDate(dto.getStartDate())
                .mvpDate(dto.getMvpDate())
                .build());
        return dto;
    }

    //모든 프로젝트 찾기, dto로 변환
    public List<ProjectDto> findAll() {
        List<Project> projects = projectRepository.findAll(); // 모든 프로젝트 엔티티

        // 프로젝트 엔티티 dto 로 변환
        return projects.stream()
                .map(project -> new ProjectDto(
                        project.getProjectName(),
                        project.getStartDate(),
                        project.getMvpDate()
                ))
                .collect(Collectors.toList()); // 모든 결과값들 dto리스트로 콜렉트
    }

    //이름으로 프로젝트 찾기, dto로 변환
    public Optional<ProjectDto> findByProjectName(String projectName){
        System.out.println("해당 프로젝트 찾는중: " + projectName); //디버깅용
        Optional<Project> projectOptional =  projectRepository.findByProjectName(projectName);
        return projectOptional.map (project -> new ProjectDto(
                project.getProjectName(),
                project.getStartDate(),
                project.getMvpDate()
        ));
    }

}