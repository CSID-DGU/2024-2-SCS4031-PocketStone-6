package com.pocketstone.team_sync.dto.projectdto;

import lombok.Data;

@Data
public class SuccessCriteriaDto {
    private Long id;
    private Long projectId;
    private String criteriaName;
    private String criteriaDescription;
    private String successCondition;
}
