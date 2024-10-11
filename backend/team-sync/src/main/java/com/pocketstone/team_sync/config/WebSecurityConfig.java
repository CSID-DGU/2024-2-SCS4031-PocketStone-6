package com.pocketstone.team_sync.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pocketstone.team_sync.config.jwt.TokenProvider;
import com.pocketstone.team_sync.repository.RefreshTokenRepository;
import com.pocketstone.team_sync.service.UserDetailService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

    private final UserDetailService userDetailService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    //정적인 자원에 대한 보안 제외
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers("/static/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable)
        .httpBasic(basic -> basic.disable())//베이직 로그인 사용 안함
        .formLogin(AbstractHttpConfigurer::disable);//폼로그인 사용안함

        http.sessionManagement(session -> session //세션을 사용하지 않음
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);//jwt필터추가


        http.authorizeHttpRequests((authz) -> authz
                .requestMatchers("/api/token","/login","/signup").permitAll() //로그인, 회원가입, 토큰 재발급만 접근허용
                .requestMatchers("/api/**").authenticated() //api로 시작하는 모든 경로 인증필요
                .anyRequest().permitAll());
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }
    
    
}
