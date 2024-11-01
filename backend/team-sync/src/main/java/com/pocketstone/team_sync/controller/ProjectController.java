package com.pocketstone.team_sync.controller;

import com.pocketstone.team_sync.dto.projectdto.*;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    //프로젝트 생성
    @PostMapping("/project")
    public ResponseEntity<ProjectDto> addProject(@AuthenticationPrincipal User user, @RequestBody ProjectDto projectDto) {
        return new ResponseEntity<>(projectService.save(user, projectDto), HttpStatus.CREATED);
    }


    //프로젝트 이름으로 프로젝트 찾기
    @GetMapping("/{projectName}")
    public ResponseEntity<ProjectDto> getProject(@AuthenticationPrincipal User user, @PathVariable String projectName) {
        return projectService.findByProjectName(user, projectName)
                .map(project -> new ResponseEntity<>(project, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); //optional 값 없을때 404 반환
    }

    //모든 프로젝트 찾기
    @GetMapping("/all")
    public ResponseEntity<List<ProjectDto>> getAllProjects(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(projectService.findAll(user), HttpStatus.OK);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<ProjectDto>> getUpcomingProjects(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(projectService.findUpcomingProjects(user), HttpStatus.OK);
    }

    @GetMapping("/ongoing")
    public ResponseEntity<List<ProjectDto>> getOngoingProjects(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(projectService.findOngoingProjects(user), HttpStatus.OK);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<ProjectDto>> getCompletedProjects(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(projectService.findCompletedProjects(user), HttpStatus.OK);
    }

    //@DeleteMapping 프로젝트 삭제
}
