package com.pocketstone.team_sync.dto.employeeDto;

import java.util.List;

import com.pocketstone.team_sync.entity.PastProject;
import com.pocketstone.team_sync.entity.enums.Role;
import com.pocketstone.team_sync.entity.enums.Skill;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmployeeSpecificationResponseDto {

    private Long id;
    private double skillScore;
    private List<Skill> skillSet;
    private double kpiScore;
    private double getPeerEvaluationScore;
    private String personalType;
    private Role role;
    private List<PastProject> pastProjects;
}
