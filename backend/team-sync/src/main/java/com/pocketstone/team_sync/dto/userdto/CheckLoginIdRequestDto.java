package com.pocketstone.team_sync.dto.userdto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CheckLoginIdRequestDto {
    @NotEmpty (message = "아이디를 입력해주세요.")
    private String loginId;
}
