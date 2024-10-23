package com.pocketstone.team_sync.controller;

import com.pocketstone.team_sync.dto.projectdto.ProjectDto;
import com.pocketstone.team_sync.dto.projectdto.ProjectWrapperDto;
import com.pocketstone.team_sync.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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


    //프로젝트 이름으로 프로젝트 찾기
    @GetMapping("/{projectName}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable String projectName) {
        return projectService.findByProjectName(projectName)
                .map(project -> new ResponseEntity<>(project, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    //모든 프로젝트 찾기
    @GetMapping("/all")
    public ResponseEntity <List<ProjectDto>> getAllProjects() {
        return new ResponseEntity<>(projectService.findAll(), HttpStatus.OK);
    }
    //@PostMapping 프로젝트 업데이트
    //@DeleteMapping 프로젝트 삭제
    //@GetMapping 프로젝트 시작일과 MVP일로 프로젝트 찾기

}
