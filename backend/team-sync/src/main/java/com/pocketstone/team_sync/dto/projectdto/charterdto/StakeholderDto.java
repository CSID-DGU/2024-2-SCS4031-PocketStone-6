package com.pocketstone.team_sync.dto.projectdto.charterdto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pocketstone.team_sync.entity.Charter.Stakeholder;
import com.pocketstone.team_sync.entity.ProjectCharter;
import lombok.Data;

@Data
public class StakeholderDto {
    private Long id;
    private String stakeholderName;
    private String stakeholderContent;

    public StakeholderDto(String stakeholderName, String stakeholderContent) {
        this.stakeholderName = stakeholderName;
        this.stakeholderContent = stakeholderContent;
    }

    @JsonCreator
    public StakeholderDto(@JsonProperty("id") Long id,
                    @JsonProperty("stakeholderName") String stakeholderName,
                    @JsonProperty("stakeholderContent") String stakeholderContent) {
        this.id = id;
        this.stakeholderName = stakeholderName;
        this.stakeholderContent = stakeholderContent;
    }

    public Stakeholder toStakeholder(ProjectCharter projectCharter, StakeholderDto stakeholderDto){
        return Stakeholder.builder()
                .projectCharter(projectCharter)
                .stakeholderName(stakeholderDto.getStakeholderName())
                .stakeholderContent(stakeholderDto.getStakeholderContent())
                .build();
    }

    public StakeholderDto toStakeholderDto(Stakeholder stakeholder){
        return new StakeholderDto(stakeholder.getStakeholderName(), stakeholder.getStakeholderContent());
    }
}
