package com.pocketstone.team_sync.controller;

import com.pocketstone.team_sync.dto.projectdto.*;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.entity.enums.ProjectStatus;
import com.pocketstone.team_sync.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    //프로젝트 생성
    @PostMapping("/project")
    public ResponseEntity<Object> addProject(@AuthenticationPrincipal User user,
                                                 @Valid @RequestBody ProjectDto projectDto) {
        return new ResponseEntity<>(projectService.save(user, projectDto), HttpStatus.CREATED);
    }


    //프로젝트 이름으로 프로젝트 찾기
    @GetMapping("/{projectName}")
    public ResponseEntity<ProjectDto> getProject(@AuthenticationPrincipal User user, @PathVariable String projectName) {
        return new ResponseEntity<>(projectService.findByProjectName(user, projectName), HttpStatus.OK);
    }

    //모든 프로젝트 찾기
    @GetMapping
    public ResponseEntity<List<ProjectDto>> getProjects(@AuthenticationPrincipal User user, @RequestParam(value = "status",required = false) ProjectStatus status) {
        status = (status == null) ? ProjectStatus.ALL : status;
        List<ProjectDto> projects = projectService.getProjectsByStatus(status, user);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    //@DeleteMapping 프로젝트 삭제
}
