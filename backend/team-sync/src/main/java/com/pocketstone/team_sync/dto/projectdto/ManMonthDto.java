package com.pocketstone.team_sync.dto.projectdto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pocketstone.team_sync.entity.ManMonth;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.Timeline;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@NoArgsConstructor
public class ManMonthDto {

    private Long id;
    private LocalDate weekStartDate;
    private LocalDate weekEndDate;
    private Double manMonth;
    public ManMonthDto(LocalDate weekStartDate, LocalDate weekEndDate, Double manMonth) {
        this.weekStartDate = weekStartDate;
        this.weekEndDate = weekEndDate;
        this.manMonth = manMonth;
    }

    @JsonCreator //json으로 받을때 id와 함께 호출하는 생성자
    public ManMonthDto(@JsonProperty("id") Long id,
                       @JsonProperty("weekStartDate") LocalDate weekStartDate,
                       @JsonProperty("weekEndDate") LocalDate weekEndDate,
                       @JsonProperty("manMonth") Double manMonth) {
        this.id = id;
        this.weekStartDate = weekStartDate;
        this.weekEndDate = weekEndDate;
        this.manMonth = manMonth;
    }

}
