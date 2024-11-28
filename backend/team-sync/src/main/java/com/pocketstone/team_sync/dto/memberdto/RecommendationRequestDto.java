package com.pocketstone.team_sync.dto.memberdto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecommendationRequestDto {

    private Long projectId;
    private List<Long> memberIds;
    public void setAvailableEmployeeIds(List<Long> ids) {
        this.memberIds = ids;
    }
}
