package com.pocketstone.team_sync.dto.projectdto.charterdto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pocketstone.team_sync.entity.Charter.Position;
import com.pocketstone.team_sync.entity.ProjectCharter;
import lombok.Data;

@Data
public class PositionDto {
    private Long id;
    private String positionName;
    private String positionContent;

    public PositionDto(String positionName, String positionContent) {
        this.positionName = positionName;
        this.positionContent = positionContent;
    }

    @JsonCreator
    public PositionDto(@JsonProperty("id") Long id,
                        @JsonProperty("positionName") String positionName,
                        @JsonProperty("positionContent") String positionContent) {
        this.id = id;
        this.positionName = positionName;
        this.positionContent = positionContent;
    }

    public Position toPosition(ProjectCharter projectCharter, PositionDto positionDto) {
        return Position.builder()
                .projectCharter(projectCharter)
                .positionName(positionDto.getPositionName())
                .positionContent(positionDto.getPositionContent())
                .build();
    }

    public PositionDto toPositionDto(Position position) {
        return new PositionDto(position.getPositionName(), position.getPositionContent());
    }
}
