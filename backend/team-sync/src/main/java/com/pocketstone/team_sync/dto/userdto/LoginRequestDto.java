package com.pocketstone.team_sync.dto.userdto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LoginRequestDto {

    private String loginId;
    private String password;
}
