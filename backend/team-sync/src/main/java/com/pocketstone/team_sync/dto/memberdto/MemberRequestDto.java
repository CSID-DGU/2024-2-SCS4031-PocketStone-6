package com.pocketstone.team_sync.dto.memberdto;

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
public class MemberRequestDto {
    private Long projectId;
    private List<Member> memberList;

    @Getter
    private static class Member {
        private Long employeeId;
    }

    // 외부에서는 long으로 조회
    @JsonIgnore 
    public List<Long> getEmployeeIds() {
        return memberList.stream()
                           .map(Member::getEmployeeId)
                           .collect(Collectors.toList());
    }

}
