package com.pocketstone.team_sync.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class LoginTokenResponseDto {

    private String grantType;
    private String accessToken;
    private String refreshToken;
}

