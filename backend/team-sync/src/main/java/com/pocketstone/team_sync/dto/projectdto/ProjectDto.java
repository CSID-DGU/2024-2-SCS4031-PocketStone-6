package com.pocketstone.team_sync.dto.projectdto;
import lombok.Data;

import java.time.LocalDate;


@Data
public class ProjectDto {
    private String projectName;
    private LocalDate startDate;
    private LocalDate mvpDate;

    public ProjectDto(String projectName, LocalDate startDate, LocalDate mvpDate) {
        this.projectName = projectName;
        this.startDate = startDate;
        this.mvpDate = mvpDate;
    }
}




