package com.pocketstone.team_sync.dto.teammatedto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddTeammateRequestDto {
    private Long projectId;
    private List<Teammate> teammateList;

    @Getter
    private static class Teammate {
        private Long employeeId;
    }

    // 외부에서는 long으로 조회
    @JsonIgnore 
    public List<Long> getEmployeeIds() {
        return teammateList.stream()
                           .map(Teammate::getEmployeeId)
                           .collect(Collectors.toList());
    }

}
