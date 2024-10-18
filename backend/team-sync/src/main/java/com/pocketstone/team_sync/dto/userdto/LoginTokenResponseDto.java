package com.pocketstone.team_sync.dto.userdto;

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

