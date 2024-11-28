package com.pocketstone.team_sync.dto.memberdto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationResponseDto {

    private List<Member> memberList;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Member {
        private Long employeeId;
    }
}
