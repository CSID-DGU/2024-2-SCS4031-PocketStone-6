package com.pocketstone.team_sync.dto.employeeDto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class EmployeeInformationResponseDto {
    private Long employeeId;
    private String staffId;
    private String name;
    private String department;
    private String position;
    private String phoneNumber;
    private String email;
    private LocalDate hireDate;
}
