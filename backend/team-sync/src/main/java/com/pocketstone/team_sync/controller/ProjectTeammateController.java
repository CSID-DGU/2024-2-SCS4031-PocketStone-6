package com.pocketstone.team_sync.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pocketstone.team_sync.dto.teammatedto.RecommendationRequestDto;
import com.pocketstone.team_sync.dto.teammatedto.RecommendationResponseDto;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.service.ProjectTeammateService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/team")
public class ProjectTeammateController {

    // 의존성
    private final ProjectTeammateService projectTeammateService;

    //팀 추천요청
    @PostMapping("/recommendation")
    public ResponseEntity<RecommendationResponseDto> recommendTeammate(@AuthenticationPrincipal User user, @RequestBody RecommendationRequestDto request) {
        return ResponseEntity.ok(projectTeammateService.recommendTeammate(user, request));
    }
    
    //팀원 저장

    // 팀원 수정

    // 팀원 삭제

    // 프로젝트 팀 조회
}
