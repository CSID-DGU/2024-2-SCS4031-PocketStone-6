package com.pocketstone.team_sync.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddUserRequestDto {
    @NotEmpty (message = "아이디를 입력해주세요")
    private String loginId;
    @Email(message = "이메일 형식이 올바르지 않습니다")
    @NotEmpty(message = "이메일 주소를 입력해주세요")
    private String email;
    //todo 비밀번호 validation 추가,
    @NotEmpty(message = "비밀번호를 설정 해 주세요")
    private String password;
    @NotEmpty(message = "회사를 설정 해 주세요")
    private String companyName;
}
