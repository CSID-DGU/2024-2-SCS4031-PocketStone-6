package com.pocketstone.team_sync.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pocketstone.team_sync.dto.MessageResponseDto;
import com.pocketstone.team_sync.dto.memberdto.MemberListResponseDto;
import com.pocketstone.team_sync.dto.memberdto.MemberRequestDto;
import com.pocketstone.team_sync.dto.memberdto.RecommendationRequestDto;
import com.pocketstone.team_sync.dto.memberdto.RecommendationResponseDto;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.service.ProjectMemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class ProjectMemberController {

    // 의존성
    private final ProjectMemberService projectMemberService;

    //팀 추천요청
    @PostMapping("/recommendation")
    public ResponseEntity<RecommendationResponseDto> recommendMember(@AuthenticationPrincipal User user, @RequestBody RecommendationRequestDto request) {
        return ResponseEntity.ok(projectMemberService.recommendMember(user, request));
    }
    
    //팀원 저장
    @PostMapping("/register")
    public ResponseEntity<MessageResponseDto> registerMember(@AuthenticationPrincipal User user, @RequestBody MemberRequestDto request) {
        projectMemberService.registerMember(user, request);
        return ResponseEntity.ok(new MessageResponseDto("팀원저장 완료"));
    }

    // 팀원 삭제(여러명 가능)
    @PostMapping("/delete")
    public ResponseEntity<MessageResponseDto> deleteMember(@AuthenticationPrincipal User user, @RequestBody MemberRequestDto request){
        projectMemberService.deleteMember(user, request);
        return ResponseEntity.ok(new MessageResponseDto("팀원삭제 완료"));
    }

    // 팀원 전체 삭제
    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponseDto> deleteAllMembers(@AuthenticationPrincipal User user, @PathVariable("projectId") Long projectId){
        projectMemberService.deleteAllMembers(user, projectId);
        return ResponseEntity.ok(new MessageResponseDto("팀원 전체 삭제 완료"));
    }

    // 프로젝트 팀 조회
    @GetMapping("/{projectId}")
    public ResponseEntity<List<MemberListResponseDto>> getMember(@AuthenticationPrincipal User user,@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(projectMemberService.getMember(user, projectId));
    }
}
