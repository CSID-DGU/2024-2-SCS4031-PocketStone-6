package com.pocketstone.team_sync.service;

import java.time.Duration;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pocketstone.team_sync.config.jwt.TokenProvider;
import com.pocketstone.team_sync.dto.userdto.AddUserRequestDto;
import com.pocketstone.team_sync.dto.userdto.LoginTokenResponseDto;
import com.pocketstone.team_sync.dto.userdto.UserInformationResponseDto;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.repository.RefreshTokenRepository;
import com.pocketstone.team_sync.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private RefreshTokenService refreshTokenService;

    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofHours(2);

    //회원가입
    @Transactional
    public Long save(AddUserRequestDto dto) {
        
        return userRepository.save(User.builder()
                .loginId(dto.getLoginId())
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))//비밀번호 암호화 저장
                .companyName(dto.getCompanyName())
                .joinDate(LocalDate.now())
                .build()).getId();
    }

    //로그인
    @Transactional
    public LoginTokenResponseDto login(String loginId, String password) {

        User user = userRepository.findByLoginId(loginId)
                .orElse(null);

        if(user == null) {
            return null;
        }



        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            //throw new BadCredentialsException("Invalid password");
            return null;
        }

        

        String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);
        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
        refreshTokenService.saveRefreshToken(user.getId(),refreshToken);
        LoginTokenResponseDto loginToken = new LoginTokenResponseDto("Bearer", accessToken, refreshToken);
        return loginToken;
    }


    public User findById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다: " + userId));
    }

    public User getUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow(() -> new UsernameNotFoundException("해당 id의 사용자가 없습니다: " + loginId));
    }

    //계정삭제
    @Transactional
    public void deleteAccount(Long userId) {
        userRepository.deleteById(userId);//계정삭제
        refreshTokenRepository.deleteByUserId(userId);//리프레시 토큰도 삭제
    }

    //유저정보 조회
    public UserInformationResponseDto getUserInfo(Long userId){
        User user = userRepository.findById(userId).orElse(null);
        if (user==null){
            //예외처리
            throw new IllegalArgumentException();
        }
        return new UserInformationResponseDto(user);

    }

}
