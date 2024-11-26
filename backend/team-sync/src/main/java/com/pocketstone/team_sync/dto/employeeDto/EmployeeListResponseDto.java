package com.pocketstone.team_sync.dto.employeeDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeeListResponseDto {
    private Long employeeId;
    private String staffId;
    private String name;
    private String department;
    private String position;
}
