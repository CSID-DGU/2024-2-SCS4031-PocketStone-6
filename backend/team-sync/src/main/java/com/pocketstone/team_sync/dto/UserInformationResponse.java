package com.pocketstone.team_sync.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInformationResponse {

    private String loginId;
    private String firstName;
    private String lastName;
}
