package com.pocketstone.team_sync.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pocketstone.team_sync.dto.MessageResponseDto;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.service.ApplicantService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/applicant")
public class ApplicantController {

    // 의존성
    private final ApplicantService applicantService;

     //지원자 엑셀 등록
    @PostMapping("/add-excel")
    public ResponseEntity<MessageResponseDto> addExcel(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile file) {
        applicantService.enrollApplicantList(user, file);
        return ResponseEntity.ok(new MessageResponseDto("등록되었습니다."));
    }

     //지원자 개인 삭제
    @DeleteMapping("/{applicantId}")
    public ResponseEntity<MessageResponseDto> deleteApplicant(@AuthenticationPrincipal User user, @PathVariable("applicantId") Long applicantId){
        applicantService.deleteApplicant(user, applicantId);
        return ResponseEntity.ok(new MessageResponseDto("해당 지원자가 삭제 되었습니다."));
    }

    //지원자 전체 삭제
    @DeleteMapping("")
    public ResponseEntity<MessageResponseDto> deleteAllApplicant(@AuthenticationPrincipal User user ){
        applicantService.deleteAllApplicant(user);
        return ResponseEntity.ok(new MessageResponseDto("모든 지원자 정보가 삭제 되었습니다."));
    }
}

