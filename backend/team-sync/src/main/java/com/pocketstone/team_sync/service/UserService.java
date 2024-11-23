package com.pocketstone.team_sync.service;

import com.pocketstone.team_sync.dto.MessageResponseDto;
import com.pocketstone.team_sync.dto.userdto.CheckEmailRequestDto;
import com.pocketstone.team_sync.dto.userdto.CheckLoginIdRequestDto;
import com.pocketstone.team_sync.exception.CredentialsInvalidException;
import com.pocketstone.team_sync.exception.EmailAlreadyExistsException;
import com.pocketstone.team_sync.exception.LoginIdAlreadyExistsException;
import com.pocketstone.team_sync.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        return userRepository.findByLoginId(loginId).orElseThrow(() -> new UserNotFoundException(loginId));
    }

    //계정삭제
    @Transactional
    public MessageResponseDto deleteAccount(User user) {
        Long userId = user.getId();
        userRepository.deleteById(userId);//계정삭제
        refreshTokenRepository.deleteByUserId(userId);//리프레시 토큰도 삭제
        companyRepository.deleteByUserId(userId);//확장시 마스터계정에서만
        return new MessageResponseDto("탈퇴처리 되었습니다.");
    }

    //유저정보 조회
    public UserInformationResponseDto getUserInfo(User user){
        Long userId = user.getId();
        User checkUser = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(""));
        return new UserInformationResponseDto(user);

    }

    public MessageResponseDto checkLoginId (CheckLoginIdRequestDto inputLoginId){
        if(!userRepository.existsByLoginId(inputLoginId.getLoginId())){
            return new MessageResponseDto("사용가능한 아이디입니다.");
    } else throw new LoginIdAlreadyExistsException();

    }

    public MessageResponseDto checkEmail (CheckEmailRequestDto inputEmail){
        if(!userRepository.existsByEmail(inputEmail.getEmail())){
            return new MessageResponseDto("사용가능한 이메일입니다.");
        }
        else throw new EmailAlreadyExistsException();
    }

}
