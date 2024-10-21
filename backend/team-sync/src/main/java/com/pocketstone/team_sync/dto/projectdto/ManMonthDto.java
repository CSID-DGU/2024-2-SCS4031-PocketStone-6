package com.pocketstone.team_sync.dto.projectdto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ManMonthDto {
    private Long id;
    private Long projectId;
    private String Position;
    private BigDecimal manMonth;

}
