package com.pocketstone.team_sync.dto;

import java.time.LocalDate;

import com.pocketstone.team_sync.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInformationResponse {

    private String loginId;
    private String companyName;
    private LocalDate joinDate;

    public UserInformationResponse(User user){
        loginId = user.getLoginId();
        companyName = user.getCompanyName();
        joinDate = user.getJoinDate();
    }
}
