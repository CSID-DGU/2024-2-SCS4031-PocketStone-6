package com.pocketstone.team_sync.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pocketstone.team_sync.dto.MessageResponseDto;
import com.pocketstone.team_sync.dto.employeeDto.EmployeeInformationResponseDto;
import com.pocketstone.team_sync.dto.employeeDto.EmployeeListResponseDto;
import com.pocketstone.team_sync.dto.employeeDto.EmployeeSpecificationResponseDto;
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
    
    //사원 등록


    //사원 삭제
    //사원 개인 삭제
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<MessageResponseDto> deleteEmployee(@AuthenticationPrincipal User user, @PathVariable("employeeId") Long employeeId){
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        if (company == null){
            return null;
        }
        employeeService.deleteEmployee(company, employeeId);
        return ResponseEntity.ok(new MessageResponseDto("해당 사원이 삭제 되었습니다."));
    }

    //사원 전체 삭제
    @DeleteMapping("")
    public ResponseEntity<MessageResponseDto> deleteAllEmployee(@AuthenticationPrincipal User user){
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        if (company == null){
            return null;
        }
        employeeService.deleteAllEmployee(company);
        return ResponseEntity.ok(new MessageResponseDto("모든 사원 정보가 삭제 되었습니다."));
    }


    //사원 기본 정보 수정



    //사원 조회

    //1.목록조회: 이름이랑 사원번호, 부서, 직책만
    @GetMapping("")
    public ResponseEntity<List<EmployeeListResponseDto>> getEmployeeList(@AuthenticationPrincipal User user){
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        List<EmployeeListResponseDto> employeeList = employeeService.getEmployees(company);
        return ResponseEntity.ok(employeeList);
    }

    //2. 개인정보 조회 - 특정인
    @GetMapping("/{employeeId}/info")
    public ResponseEntity<EmployeeInformationResponseDto> getInfo(@AuthenticationPrincipal User user, @PathVariable("employeeId") Long employeeId){
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        EmployeeInformationResponseDto response = employeeService.getEmployeeInfo(company, employeeId);
        return ResponseEntity.ok(response);
    }

    //3. 이력 조회 - 특정인

    @GetMapping("/{employeeId}/spec")
    public ResponseEntity<EmployeeSpecificationResponseDto> getSpecification(@AuthenticationPrincipal User user, @PathVariable("employeeId") Long employeeId){
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        EmployeeSpecificationResponseDto response = employeeService.getEmployeeSpec(company, employeeId);
        return ResponseEntity.ok(response);
    }
}
