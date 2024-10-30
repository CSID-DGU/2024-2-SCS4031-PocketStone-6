package com.pocketstone.team_sync.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pocketstone.team_sync.dto.MessageResponseDto;
import com.pocketstone.team_sync.dto.employeeDto.EmployeeListResponse;
import com.pocketstone.team_sync.entity.Company;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.repository.CompanyRepository;
import com.pocketstone.team_sync.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {


    // 의존성
    private final EmployeeService employeeService;
    private final CompanyRepository companyRepository;

     //사원 엑셀 등록
    @PostMapping("/add-excel")
    public ResponseEntity<MessageResponseDto> addExcel(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile file) {
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        employeeService.enrollEmployeeList(company, file);
        return ResponseEntity.ok(new MessageResponseDto("등록되었습니다."));
    }

    //사원 목록 조회
    //1. 이름이랑 사원번호, 부서, 직책만
    //2. 개인정보 조회 - 특정인
    //3. 이력 조회 - 특정인
    @GetMapping("/")
    public ResponseEntity<EmployeeListResponse> getEmployeeList(@AuthenticationPrincipal User user){
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        EmployeeListResponse response = new EmployeeListResponse(employeeService.getEmployees(company));
        return ResponseEntity.ok(response);
    }
}
