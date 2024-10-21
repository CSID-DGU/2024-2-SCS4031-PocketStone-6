package com.pocketstone.team_sync.dto.projectdto;

import lombok.Data;

@Data
public class ProjectCharterDto {

    private Long id;
    private Long projectId;
    private String teamPositions;
    private String vision;
    private String objective;
    private String stakeholder;
    private String scope;
    private String risk;
    private String principle;
}
