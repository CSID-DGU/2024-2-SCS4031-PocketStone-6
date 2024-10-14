package com.pocketstone.team_sync.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddUserRequest {
    private String loginId;
    private String email;
    private String password;
    private String companyName;
}
