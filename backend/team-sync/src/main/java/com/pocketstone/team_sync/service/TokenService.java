package com.pocketstone.team_sync.service;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.pocketstone.team_sync.config.jwt.TokenProvider;
import com.pocketstone.team_sync.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    //새로운 엑세스토큰 발급
    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId(); //저장된 리프레시 토큰인지 확인
        User user = userService.findById(userId); //사용자
        return tokenProvider.generateToken(user, Duration.ofHours(2)); //새로운 엑세스토큰 발급
    }

    // //리프레시 토큰 
    // public void blacklistToken(String token) {
    //     // 
    //     throw new UnsupportedOperationException("Unimplemented method 'blacklistToken'");
    // }
}
