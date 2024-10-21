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

    public List<Project> findAll(){
        return projectRepository.findAll();
    }

    public Optional<Project> findByProjectName(String projectName){
        return projectRepository.findByProjectName(projectName);
    }






}