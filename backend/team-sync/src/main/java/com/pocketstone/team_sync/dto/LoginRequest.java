package com.pocketstone.team_sync.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class LoginRequest {

    private String loginId;
    private String password;
}
