package com.pocketstone.team_sync.dto.memberdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationRequestDto {

    private Long projectId;
    private int member;
}
