package com.pocketstone.team_sync.dto.projectdto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.Timeline;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TimelineDto {
    private Long id;
    @Min(value = 0)
    @Max(value = 100)
    @NotNull
    private Integer sprintOrder;
    @NotEmpty
    private String sprintContent;
    @Min(value = 1)
    @Max(value = 54)
    @NotNull
    private Integer sprintDurationWeek;


    //타임라인 첫 생성시 사용하는 생성자
    public TimelineDto(Integer sprintOrder, String sprintContent, Integer sprintDurationWeek) {
        this.sprintOrder = sprintOrder;
        this.sprintContent = sprintContent;
        this.sprintDurationWeek = sprintDurationWeek;
    }

    @JsonCreator //json으로 받을때 id와 함께 호출하는 생성자
    public TimelineDto(@JsonProperty("id") Long id,
                       @JsonProperty("sprintOrder") Integer sprintOrder,
                       @JsonProperty("sprintContent") String sprintContent,
                       @JsonProperty("sprintDurationWeek") Integer sprintDurationWeek) {
        this.id = id;
        this.sprintOrder = sprintOrder;
        this.sprintContent = sprintContent;
        this.sprintDurationWeek = sprintDurationWeek;
    }

    //타임라인Dto 엔티티로 변환
    public Timeline toTimeline(Project project, TimelineDto timelineDto) {
        return Timeline.builder()
                .project(project)
                .sprintOrder(timelineDto.getSprintOrder())
                .sprintContent(timelineDto.getSprintContent())
                .sprintDurationWeek(timelineDto.getSprintDurationWeek())
                .build();
    }

}


