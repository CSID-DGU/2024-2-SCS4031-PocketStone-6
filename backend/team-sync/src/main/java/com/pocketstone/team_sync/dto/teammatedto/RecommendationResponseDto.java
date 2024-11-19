package com.pocketstone.team_sync.dto.teammatedto;

import java.util.List;

import com.pocketstone.team_sync.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecommendationResponseDto {

    private List<Employee> employeeList;
}
