package com.pocketstone.team_sync.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pocketstone.team_sync.dto.userdto.UserInformationResponseDto;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.repository.CompanyRepository;
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
    private CompanyRepository companyRepository;



    public User findById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다: " + userId));
    }

    public User getUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow(() -> new UsernameNotFoundException("해당 id의 사용자가 없습니다: " + loginId));
    }

    public boolean checkLoginId(String loginId){
        return userRepository.existsByLoginId(loginId);
    }

    public boolean checkEmail(String email){
        return userRepository.existsByEmail(email);
    }
    
    //계정삭제
    @Transactional
    public void deleteAccount(Long userId) {
        userRepository.deleteById(userId);//계정삭제
        refreshTokenRepository.deleteByUserId(userId);//리프레시 토큰도 삭제
        companyRepository.deleteByUserId(userId);//확장시 마스터계정에서만
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
