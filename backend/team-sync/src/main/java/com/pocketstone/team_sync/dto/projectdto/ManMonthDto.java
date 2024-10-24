package com.pocketstone.team_sync.dto.projectdto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ManMonthDto {
    private String Position;
    private BigDecimal manMonth;

    public ManMonthDto(String position, BigDecimal manMonth) {
        this.Position = position;
        this.manMonth = manMonth;
    }
}
