package com.pocketstone.team_sync.dto.eval;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeerEvalDto {
    private Long id;
    private Long projectMemberId;
    private Double performanceScore;
    private Double interpersonalScore;
    private Double expertiseScore;
    private Double responsibilityScore;
}