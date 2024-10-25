package com.pocketstone.team_sync.dto.userdto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddUserRequestDto {
    private String loginId;
    private String email;
    private String password;
    private String companyName;
}
