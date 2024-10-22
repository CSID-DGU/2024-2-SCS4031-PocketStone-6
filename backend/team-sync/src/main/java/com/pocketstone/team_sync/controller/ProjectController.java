package com.pocketstone.team_sync.controller;

import com.pocketstone.team_sync.dto.projectdto.ProjectDto;
import com.pocketstone.team_sync.dto.projectdto.ProjectWrapperDto;
import com.pocketstone.team_sync.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    //프로젝트 생성
    @PostMapping("/add")
    public ResponseEntity<ProjectDto> addProject(@RequestBody ProjectWrapperDto projectWrapperDto) {
        ProjectDto projectDto = projectWrapperDto.getProjectDto();
        return new ResponseEntity<>(projectService.save(projectDto), HttpStatus.CREATED);
    }
    //@GetMapping
}
