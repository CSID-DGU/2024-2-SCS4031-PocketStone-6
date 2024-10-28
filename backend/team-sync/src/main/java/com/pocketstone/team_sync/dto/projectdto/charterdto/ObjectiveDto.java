package com.pocketstone.team_sync.dto.projectdto.charterdto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pocketstone.team_sync.entity.Charter.Objective;
import com.pocketstone.team_sync.entity.ProjectCharter;
import lombok.Data;

@Data
public class ObjectiveDto {
    private Long id;
    private String objectiveName;
    private String objectiveContent;

    public ObjectiveDto(String objectiveName, String objectiveContent) {
        this.objectiveName = objectiveName;
        this.objectiveContent = objectiveContent;
    }
    @JsonCreator
    public ObjectiveDto(@JsonProperty("id") Long id,
                        @JsonProperty("objectiveName") String objectiveName,
                        @JsonProperty("objectiveContent") String objectiveContent) {
        this.id = id;
        this.objectiveName = objectiveName;
        this.objectiveContent = objectiveContent;
    }


    public Objective toObjective(ProjectCharter projectCharter, ObjectiveDto objectiveDto) {
        return Objective.builder()
                .projectCharter(projectCharter)
                .objectiveName(objectiveDto.getObjectiveName())
                .objectiveContent(objectiveDto.getObjectiveContent())
                .build();
    }

    public ObjectiveDto toObjectiveDto(Objective objective) {
        return new ObjectiveDto(objective.getObjectiveName(), objective.getObjectiveContent());
    }

}
