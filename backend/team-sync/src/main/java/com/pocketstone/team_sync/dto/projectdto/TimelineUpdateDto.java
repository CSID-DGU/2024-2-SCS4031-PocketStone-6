package com.pocketstone.team_sync.dto.projectdto;

import lombok.Data;

@Data //타임라인 조회,삭제,업데이트를 위한 dto
public class TimelineUpdateDto {
    private Long id;
    private Integer sprintOrder;
    private String sprintContent;
    private Integer sprintDurationWeek;

    public TimelineUpdateDto(Long id, Integer sprintOrder, String sprintContent, Integer sprintDurationWeek) {
        this.id = id;
        this.sprintOrder = sprintOrder;
        this.sprintContent = sprintContent;
        this.sprintDurationWeek = sprintDurationWeek;
    }


}
