package com.pocketstone.team_sync.dto.projectdto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pocketstone.team_sync.entity.ManMonth;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.Timeline;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
public class ManMonthDto {

    private Long id;
    private String position;
    private BigDecimal manMonth;

    //맨먼스 초기 생성시 사용하는 생성자
    public ManMonthDto(String position, BigDecimal manMonth) {
        this.position = position;
        this.manMonth = manMonth;
    }

    @JsonCreator //json으로 받을때 id와 함께 호출하는 생성자
    public ManMonthDto(@JsonProperty("id") Long id,
                       @JsonProperty("position") String position,
                       @JsonProperty("manMonth") BigDecimal manMonth) {
        this.id = id;
        this.position = position;
        this.manMonth = manMonth;
    }

    //맨먼스Dto 엔티티로 변환
    public ManMonth toManMonth(Project project, Timeline timeline, ManMonthDto manMonthDto) {
        return ManMonth.builder().project(project)
                .timeline(timeline)
                .position(manMonthDto.getPosition())
                .manMonth(manMonthDto.getManMonth())
                .build();
    }
}
