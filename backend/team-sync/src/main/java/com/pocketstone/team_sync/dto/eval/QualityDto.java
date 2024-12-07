package com.pocketstone.team_sync.dto.eval;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QualityDto {
    private Long id;
    private Long projectId;
    private Double testCoverage;
    private Double productQuality;
    private Integer foundBugs;
    private Double performance;
    private Double reliability;
}