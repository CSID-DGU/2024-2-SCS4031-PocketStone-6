package com.pocketstone.team_sync.dto.projectdto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pocketstone.team_sync.entity.ManMonth;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.Timeline;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;


@Data
@NoArgsConstructor
public class ManMonthDto {

    private Long id;
    private String position;
    private BigDecimal manMonth;

    public ManMonthDto(String position, BigDecimal manMonth) {
        this.position = position;
        this.manMonth = manMonth;
    }

    @JsonCreator // Indicates that this constructor should be used for deserialization
    public ManMonthDto(@JsonProperty("id") Long id,
                       @JsonProperty("position") String position,
                       @JsonProperty("manMonth") BigDecimal manMonth) {
        this.id = id;
        this.position = position;
        this.manMonth = manMonth;
    }


     public ManMonth toManMonth(Project project, Timeline timeline, ManMonthDto manMonthDto) {
        return ManMonth.builder().project(project)
                .timeline(timeline)
                .position(manMonthDto.getPosition())
                .manMonth(manMonthDto.getManMonth())
                .build();
    }
}
