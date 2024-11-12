package com.pocketstone.team_sync.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pocketstone.team_sync.dto.MessageResponseDto;
import com.pocketstone.team_sync.dto.userdto.CheckEmailRequestDto;
import com.pocketstone.team_sync.dto.userdto.CheckLoginIdRequestDto;
import com.pocketstone.team_sync.dto.userdto.UserInformationResponseDto;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.repository.UserRepository;
import com.pocketstone.team_sync.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    //아이디 중복확인
    @PostMapping("/check-loginid")
    public ResponseEntity<MessageResponseDto> checkLoginId(@RequestBody CheckLoginIdRequestDto inputLoginId) {
        return new ResponseEntity<>(userService.checkLoginId(inputLoginId),HttpStatus.OK);
    }


    //이메일 중복확인
    @PostMapping("/check-email")
    public ResponseEntity<MessageResponseDto> checkEmail(@Valid @RequestBody CheckEmailRequestDto inputEmail) {
        return new ResponseEntity<>(userService.checkEmail(inputEmail),HttpStatus.OK) ;
    }


    //회원탈퇴
    @DeleteMapping("/withdraw")
    public ResponseEntity<MessageResponseDto> deleteAccount(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userService.deleteAccount(user),HttpStatus.OK);
    }


    //유저 정보 조회
    @GetMapping("/me")
    public ResponseEntity<UserInformationResponseDto> getUserInfo(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userService.getUserInfo(user),HttpStatus.OK);
    }


}
