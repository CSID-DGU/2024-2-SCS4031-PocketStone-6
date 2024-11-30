package com.pocketstone.team_sync.dto.projectdto;

import lombok.Data;

@Data
public class ProjectWrapperDto {
    private ProjectDto projectDto;
    private ProjectCharterDto projectCharterDto;
    private SuccessCriteriaDto successCriteriaDto;
    private TimelineDto timelineDto;
    //private ManMonthDto manMonthDto;
}
