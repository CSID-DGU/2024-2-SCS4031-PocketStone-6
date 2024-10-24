package com.pocketstone.team_sync.dto.projectdto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ManMonthUpdateDto {
    Long id;
    String position;
    BigDecimal manMonth;

    public ManMonthUpdateDto(Long id, String position, BigDecimal manMonth) {
        this.id = id;
        this.position = position;
        this.manMonth = manMonth;
    }
}
