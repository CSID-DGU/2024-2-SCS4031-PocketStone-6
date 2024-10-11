package com.pocketstone.team_sync.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.repository.UserRepository;

import lombok.RequiredArgsConstructor;

//스프링시큐리티에서 사용자 정보를 가져오는 인터페이스
@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService{

    private final UserRepository userRepository;

    //사용자 로그인아이디로 사용자 정보를 가져오는 메소드
    @Override
    public User loadUserByUsername(String loginId) {
        return userRepository.findByLoginId(loginId)
            .orElseThrow(() -> new IllegalArgumentException((loginId)));//해당아이디로 사용자를 찾을 수 없음(예외)
    }

}
