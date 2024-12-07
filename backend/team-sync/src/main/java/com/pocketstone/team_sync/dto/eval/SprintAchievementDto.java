package com.pocketstone.team_sync.dto.eval;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SprintAchievementDto {
    private Long id;
    private Long timelineId;
    private Double burndownRate;
}