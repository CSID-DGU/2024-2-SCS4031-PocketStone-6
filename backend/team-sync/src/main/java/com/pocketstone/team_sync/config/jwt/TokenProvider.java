package com.pocketstone.team_sync.config.jwt;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;

    public String generateToken(User user, Duration expiredAt) { //토큰 발급
        Date now = new Date(); //현재시간에서
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    //토큰생성 메소드
    private String makeToken(Date expiry, User user) {
        Date now = new Date();

        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)//헤더 jwt로 설정
            .setIssuer(jwtProperties.getIssuer())//발급자
            .setIssuedAt(now)//발급시간=현재
            .setExpiration(expiry)//만료시간
            .setSubject(user.getLoginId())//로그인 아이디
            .claim("id", user.getId()) //사용자 아이디
            .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret()) //비밀키로 서명
            .compact();
    }

    //jwt토큰 유효성 검증 메서드
    public boolean validToken(String token) {
        try {
            Jwts.parser() //파서 객체 생성
                .setSigningKey(jwtProperties.getSecret()) //비밀키로 복호화
                .parseClaimsJws(token); //서명검증하여 토큰 클레임 확인(만료시간확인)
            return true; //토큰유효
        } catch (Exception e) {
            return false; //토큰이 유효하지 않음
        }
    }

    // 토큰 기반으로 인증 정보를 가져오는 메서드
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")); //사용자권한집합-일반사용자
        //인증된 사용자 정보
        //return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities), token, authorities);
        // 토큰에서 사용자 정보 추출
        String loginId = claims.getSubject(); // 토큰의 서브젝트에 loginId가 저장되어 있다고 가정
        User user = userRepository.findByLoginId(loginId)
            .orElseThrow(() -> new IllegalArgumentException("해당 아이디로 사용자를 찾을 수 없습니다: " + loginId));
        return new UsernamePasswordAuthenticationToken(user, token, authorities);
    }

    //토큰 기반으로 유저 아이디를 가져오는 메소드
    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    //클래임을 가져오는 메소드
    private Claims getClaims (String token) {
        return Jwts.parser()
            .setSigningKey(jwtProperties.getSecret())
            .parseClaimsJws(token)
            .getBody();
    }
}
