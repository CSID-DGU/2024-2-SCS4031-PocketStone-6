package com.pocketstone.team_sync.dto.memberdto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationResponseDto {

    private List<Team> teams;

     @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Team {

        @JsonProperty("team_indices")
        private List<Long> teamIndices;

        @JsonProperty("skill_score")
        private double skillScore;

        @JsonProperty("project_fit_score")
        private double projectFitScore;

        @JsonProperty("avg_personality_similarity")
        private double avgPersonalitySimilarity;

        @JsonProperty("scaled_personality_similarity")
        private double scaledPersonalitySimilarity;

        @JsonProperty("kpi_score")
        private double kpiScore;

        @JsonProperty("peer_evaluation_score")
        private double peerEvaluationScore;

        @JsonProperty("final_score")
        private double finalScore;
    }

}
