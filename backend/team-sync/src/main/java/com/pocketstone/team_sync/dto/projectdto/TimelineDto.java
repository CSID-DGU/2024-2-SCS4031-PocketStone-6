package com.pocketstone.team_sync.dto.projectdto;

import lombok.Data;

@Data
public class TimelineDto {
    private Long id;
    private Long project;
    private Integer sprintOrder;
    private String sprintContent;
    private String sprintDurationWeek;
}
