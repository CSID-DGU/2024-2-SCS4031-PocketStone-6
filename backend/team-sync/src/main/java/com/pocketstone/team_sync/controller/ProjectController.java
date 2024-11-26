package com.pocketstone.team_sync.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pocketstone.team_sync.dto.projectdto.ProjectDto;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.entity.enums.ProjectStatus;
import com.pocketstone.team_sync.entity.enums.Skill;
import com.pocketstone.team_sync.service.ProjectService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


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

    @GetMapping("/id/{projectId}")
    public ResponseEntity<ProjectDto> getProjectById(@AuthenticationPrincipal User user, @PathVariable Long projectId) {
        return new ResponseEntity<>(projectService.findByProjectId(user, projectId), HttpStatus.OK);
    }

    //모든 프로젝트 찾기
    @GetMapping
    public ResponseEntity<List<ProjectDto>> getProjects(@AuthenticationPrincipal User user, @RequestParam(value = "status",required = false) ProjectStatus status) {
        status = (status == null) ? ProjectStatus.ALL : status;
        List<ProjectDto> projects = projectService.getProjectsByStatus(status, user);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    //@DeleteMapping 프로젝트 삭제

    //스킬목록 조회
    @GetMapping("/skills")
    public ResponseEntity<Map<String, List<String>>> getSkillsGroupedByRoleLabel() {
        Map<String, List<String>> skillsByRoleLabel = Skill.getSkills();
        return ResponseEntity.ok(skillsByRoleLabel);
    }
}
