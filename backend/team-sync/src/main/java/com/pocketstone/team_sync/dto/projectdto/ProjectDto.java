package com.pocketstone.team_sync.dto.projectdto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;


@Data
public class ProjectDto {
    private Long id;
    private String projectName;
    private LocalDate startDate;
    private LocalDate mvpDate;

    public ProjectDto(String projectName, LocalDate startDate, LocalDate mvpDate) {
        this.projectName = projectName;
        this.startDate = startDate;
        this.mvpDate = mvpDate;
    }

    @JsonCreator
    public ProjectDto(@JsonProperty("id") Long id,
                       @JsonProperty("projectName") String projectName,
                       @JsonProperty("startDate") LocalDate startDate,
                       @JsonProperty("mvpDate") LocalDate mvpDate) {
        this.id = id;
        this.projectName = projectName;
        this.startDate = startDate;
        this.mvpDate = mvpDate;
    }
}




