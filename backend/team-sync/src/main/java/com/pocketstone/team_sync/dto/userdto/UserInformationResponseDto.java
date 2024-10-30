package com.pocketstone.team_sync.dto.userdto;

import java.time.LocalDate;

import com.pocketstone.team_sync.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInformationResponseDto {

    private String loginId;
    private String companyName;
    private LocalDate joinDate;

    public UserInformationResponseDto(User user){
        loginId = user.getLoginId();
        companyName = user.getCompany().getCompanyName();
        joinDate = user.getJoinDate();
    }
}
