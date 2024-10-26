package com.pocketstone.team_sync.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pocketstone.team_sync.dto.MessageResponseDto;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {


    // 의존성
    private final EmployeeService employeeService;


     //사원 엑셀 등록
    @PostMapping("/add-excel")
    public ResponseEntity<MessageResponseDto> addExcel(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile file) {
        Long userId = user.getId();
        employeeService.enrollEmployeeList(userId, file);
        return ResponseEntity.ok(new MessageResponseDto("등록되었습니다."));
    }
}
