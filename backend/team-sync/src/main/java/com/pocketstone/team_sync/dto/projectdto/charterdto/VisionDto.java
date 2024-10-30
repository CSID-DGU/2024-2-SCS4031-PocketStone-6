package com.pocketstone.team_sync.dto.projectdto.charterdto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pocketstone.team_sync.entity.charter.Vision;
import com.pocketstone.team_sync.entity.ProjectCharter;
import lombok.Data;

@Data
public class VisionDto {
    private Long id;
    private String visionName;
    private String visionContent;

    public VisionDto(String visionName, String visionContent) {
        this.visionName = visionName;
        this.visionContent = visionContent;
    }

    @JsonCreator
    public VisionDto(@JsonProperty("id") Long id,
                    @JsonProperty("visionName") String visionName,
                    @JsonProperty("visionContent") String visionContent) {
        this.id = id;
        this.visionName = visionName;
        this.visionContent = visionContent;
    }

    public Vision toVision(ProjectCharter projectCharter, VisionDto visionDto) {
        return Vision.builder()
                .projectCharter(projectCharter)
                .visionName(visionDto.getVisionName())
                .visionContent(visionDto.getVisionContent())
                .build();
    }

    public VisionDto toVisionDto(Vision vision) {
        return new VisionDto(vision.getVisionName(), vision.getVisionContent());
    }
}
