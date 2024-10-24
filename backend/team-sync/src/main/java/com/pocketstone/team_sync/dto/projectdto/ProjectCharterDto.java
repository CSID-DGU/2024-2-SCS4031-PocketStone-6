package com.pocketstone.team_sync.dto.projectdto;

import lombok.Data;

@Data
public class ProjectCharterDto {

    private String teamPositions;
    private String vision;
    private String objective;
    private String stakeholder;
    private String scope;
    private String risk;
    private String principle;

    public ProjectCharterDto(String teamPositions, String vision, String objective, String stakeholder, String scope, String risk, String principle) {
        this.teamPositions = teamPositions;
        this.vision = vision;
        this.objective = objective;
        this.stakeholder = stakeholder;
        this.scope = scope;
        this.risk = risk;
        this.principle = principle;
    }
}
