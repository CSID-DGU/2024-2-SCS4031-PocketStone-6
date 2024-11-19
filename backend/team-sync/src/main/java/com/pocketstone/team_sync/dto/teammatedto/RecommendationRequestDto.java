package com.pocketstone.team_sync.dto.teammatedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationRequestDto {

    private int projectId;
    private int member;
}
