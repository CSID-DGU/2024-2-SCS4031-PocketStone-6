package com.pocketstone.team_sync.config.jwt;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter{
    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";//공백있어야 토큰만 추출 가능

    @Override
    protected void doFilterInternal(
        
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION); // 헤더에서 인증 파트 가져오기
        String token = getAccessToken(authorizationHeader); //엑세스토큰
        if (tokenProvider.validToken(token)){ //엑세스토큰 유효성 검사
            Authentication authentication = tokenProvider.getAuthentication(token); //사용자 권한
            SecurityContextHolder.getContext().setAuthentication(authentication); //권한부여
        }

        filterChain.doFilter(request,response);
    }

    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)){ //bearer로 시작하는
            return authorizationHeader.substring(TOKEN_PREFIX.length()); //bearer부분 제외하고 토큰만 반환
        }
        return null;
    }

}
