package com.pocketstone.team_sync.dto.projectdto.charterdto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pocketstone.team_sync.entity.charter.Risk;
import com.pocketstone.team_sync.entity.ProjectCharter;
import lombok.Data;

@Data
public class RiskDto {
    private Long id;
    private String riskName;
    private String riskContent;

    public RiskDto(String riskName, String riskContent) {
        this.riskName = riskName;
        this.riskContent = riskContent;
    }

    @JsonCreator
    public RiskDto(@JsonProperty("id") Long id,
                    @JsonProperty("riskName") String riskName,
                    @JsonProperty("riskContent") String riskContent) {
        this.id = id;
        this.riskName = riskName;
        this.riskContent = riskContent;
    }

    public Risk toRisk(ProjectCharter projectCharter, RiskDto riskDto) {
        return Risk.builder()
                .projectCharter(projectCharter)
                .riskName(riskDto.getRiskName())
                .riskContent(riskDto.getRiskContent())
                .build();
    }

    public RiskDto toRiskDto(Risk risk) {
        return new RiskDto(risk.getRiskName(), risk.getRiskContent());
    }
}
