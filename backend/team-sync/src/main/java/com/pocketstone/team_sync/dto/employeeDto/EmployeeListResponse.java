package com.pocketstone.team_sync.dto.employeeDto;

import java.util.List;

import com.pocketstone.team_sync.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeeListResponse {
    private List<Employee> employeeList;
}
