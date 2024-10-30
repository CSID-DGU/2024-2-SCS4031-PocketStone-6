package com.pocketstone.team_sync.dto.projectdto.charterdto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pocketstone.team_sync.entity.charter.Principle;
import com.pocketstone.team_sync.entity.ProjectCharter;
import lombok.Data;

@Data
public class PrincipleDto {
    private Long id;
    private String principleName;
    private String principleContent;

    public PrincipleDto(String principleName, String principleContent) {
        this.principleName = principleName;
        this.principleContent = principleContent;
    }

    @JsonCreator
    public PrincipleDto(@JsonProperty("id") Long id,
                        @JsonProperty("principleName") String principleName,
                        @JsonProperty("principleContent") String principleContent) {
        this.id = id;
        this.principleName = principleName;
        this.principleContent = principleContent;
    }

    public Principle toPrinciple(ProjectCharter projectCharter, PrincipleDto principleDto) {
        return Principle.builder()
                .projectCharter(projectCharter)
                .principleName(principleDto.getPrincipleName())
                .principleContent(principleDto.getPrincipleContent())
                .build();
    }

    public PrincipleDto toPrincipleDto(Principle principle) {
        return new PrincipleDto(principle.getPrincipleName(), principle.getPrincipleContent());
    }
}
