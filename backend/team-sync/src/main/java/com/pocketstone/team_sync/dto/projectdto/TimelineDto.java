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

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TimelineDto {
    private Long id;
    @Min(value = 0, message = "스프린트는 0번부터 시작합니다.")
    @Max(value = 100, message = "스프린트는 100번까지 설정 가능합니다.")
    @NotNull (message = "스프린트 순서를 입력해주세요.")
    private Integer sprintOrder;
    @NotEmpty (message = "스프린트 내용을 입력해주세요.")
    private String sprintContent;
    @NotNull (message = "스프린트 시작일을 입력해주세요.")
    private LocalDate sprintStartDate;
    @NotNull (message = "스프린트 시작일을 입력해주세요.")
    private LocalDate sprintEndDate;
    private Double requiredManmonth;


    //타임라인 첫 생성시 사용하는 생성자
    public TimelineDto(Integer sprintOrder, String sprintContent, LocalDate sprintStartDate, LocalDate sprintEndDate, Double requiredManmonth) {
        this.sprintOrder = sprintOrder;
        this.sprintContent = sprintContent;
        this.sprintStartDate = sprintStartDate;
        this.sprintEndDate = sprintEndDate;
        this.requiredManmonth = requiredManmonth;
    }

    @JsonCreator //json으로 받을때 id와 함께 호출하는 생성자
    public TimelineDto(@JsonProperty("id") Long id,
                       @JsonProperty("sprintOrder") Integer sprintOrder,
                       @JsonProperty("sprintContent") String sprintContent,
                       @JsonProperty("sprintStartDate") LocalDate sprintStartDate,
                       @JsonProperty("sprintEndDate") LocalDate sprintEndDate,
                       @JsonProperty("requiredManmonth") Double requiredManmonth) {
        this.id = id;
        this.sprintOrder = sprintOrder;
        this.sprintContent = sprintContent;
        this.sprintStartDate = sprintStartDate;
        this.sprintEndDate = sprintEndDate;
        this.requiredManmonth = requiredManmonth;
    }

    //타임라인Dto 엔티티로 변환
    public Timeline toTimeline(Project project, TimelineDto timelineDto) {
        return Timeline.builder()
                .project(project)
                .sprintOrder(timelineDto.getSprintOrder())
                .sprintContent(timelineDto.getSprintContent())
                .sprintStartDate(timelineDto.getSprintStartDate())
                .sprintEndDate(timelineDto.getSprintEndDate())
                .requiredManmonth(timelineDto.getRequiredManmonth())
                .build();
    }

}


