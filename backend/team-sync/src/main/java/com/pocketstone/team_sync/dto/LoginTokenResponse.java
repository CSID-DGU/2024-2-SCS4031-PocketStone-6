package com.pocketstone.team_sync.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class LoginTokenResponse {

    private String grantType;
    private String accessToken;
    private String refreshToken;
}

