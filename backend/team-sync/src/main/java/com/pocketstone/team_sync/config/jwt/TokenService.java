package com.pocketstone.team_sync.config.jwt;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.pocketstone.team_sync.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;


    //새로운 엑세스토큰 발급
    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        User user = refreshTokenService.findByRefreshToken(refreshToken).getUser(); //저장된 리프레시 토큰인지 확인

        return tokenProvider.generateToken(user, Duration.ofHours(2)); //새로운 엑세스토큰 발급
    }

}
