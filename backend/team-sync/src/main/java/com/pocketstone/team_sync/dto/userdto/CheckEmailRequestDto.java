package com.pocketstone.team_sync.dto.userdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class CheckEmailRequestDto {
    @Email (message = "이메일 형식이 올바르지 않습니다.")
    @NotEmpty(message = "이메일 주소를 입력해주세요.")
    private String email;
}
