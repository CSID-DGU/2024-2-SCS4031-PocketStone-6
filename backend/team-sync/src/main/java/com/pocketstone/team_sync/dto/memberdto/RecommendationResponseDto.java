package com.pocketstone.team_sync.dto.memberdto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationResponseDto {
    private List<Long> memberIds;
    private List<Long> employeeIds;
}
