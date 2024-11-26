package com.pocketstone.team_sync.auth;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pocketstone.team_sync.config.jwt.TokenService;
import com.pocketstone.team_sync.dto.MessageResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private TokenService tokenService;

        //회원가입
    @PostMapping("/signup")
    public ResponseEntity<MessageResponseDto> registerUser(@Valid @RequestBody AddUserRequestDto request) {
        try {
            authService.save(request);
            return ResponseEntity.ok(new MessageResponseDto("가입이 성공적으로 완료되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
        
    }
    //로그인
    @PostMapping("/login")
    public ResponseEntity<LoginTokenResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        String loginId = loginRequest.getLoginId();
        
        String password = loginRequest.getPassword();
        
        LoginTokenResponseDto loginToken = authService.login(loginId, password);
        return ResponseEntity.ok(loginToken);
        
    }

    
    //토큰 재요청
    @PostMapping("/refresh")
    public ResponseEntity<CreateAccessTokenResponseDto> createNewAccessToken(@RequestBody CreateAccessTokenRequestDto request) {
        try {
            String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());
            return ResponseEntity.status(HttpStatus.CREATED).body(new CreateAccessTokenResponseDto(newAccessToken));
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
        
    }

    
}
