package com.pocketstone.team_sync.dto.userdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccessTokenRequestDto {

    private String refreshToken;
}
