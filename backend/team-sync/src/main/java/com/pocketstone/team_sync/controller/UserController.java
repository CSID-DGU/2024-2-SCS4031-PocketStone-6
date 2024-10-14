package com.pocketstone.team_sync.controller;

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

import com.pocketstone.team_sync.dto.AddUserRequest;
import com.pocketstone.team_sync.dto.CheckEmailRequest;
import com.pocketstone.team_sync.dto.CheckLoginIdRequest;
import com.pocketstone.team_sync.dto.CreateAccessTokenRequest;
import com.pocketstone.team_sync.dto.CreateAccessTokenResponse;
import com.pocketstone.team_sync.dto.LoginRequest;
import com.pocketstone.team_sync.dto.LoginTokenResponse;
import com.pocketstone.team_sync.dto.MessageResponse;
import com.pocketstone.team_sync.dto.UserInformationResponse;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.repository.UserRepository;
import com.pocketstone.team_sync.service.TokenService;
import com.pocketstone.team_sync.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    //아이디 중복확인
    @PostMapping("/check-loginid")
    public ResponseEntity<MessageResponse> checkLoginId(@RequestBody CheckLoginIdRequest inputLoginId) {
        if(!userRepository.existsByLoginId(inputLoginId.getLoginId())){
            return ResponseEntity.ok(new MessageResponse("사용가능한 아이디입니다."));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("이미 사용 중인 아이디입니다."));
}

    //이메일 중복확인
    @PostMapping("/check-email")
    public ResponseEntity<MessageResponse> checkEmail(@RequestBody CheckEmailRequest inputEmail) {
        if(!userRepository.existsByEmail(inputEmail.getEmail())){
            return ResponseEntity.ok(new MessageResponse("사용가능한 이메일입니다."));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("이미 등록된 이메일입니다."));
    
    }


    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody AddUserRequest request) {
        try {
            userService.save(request);
            return ResponseEntity.ok(new MessageResponse("가입이 성공적으로 완료되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
        
    }

    //회원탈퇴
    @DeleteMapping("/withdraw")
    public ResponseEntity<MessageResponse> deleteAccount(@AuthenticationPrincipal User user) {
        Long userId = user.getId();
        userService.deleteAccount(userId);
        return ResponseEntity.ok(new MessageResponse("탈퇴처리 되었습니다."));
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<LoginTokenResponse> login(@RequestBody LoginRequest loginRequest) {
        String loginId = loginRequest.getLoginId();
        
        String password = loginRequest.getPassword();
        
        LoginTokenResponse loginToken = userService.login(loginId, password);
        if (loginToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(loginToken);
        
    }

    
    //토큰 재요청
    @PostMapping("/refresh")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest request) {
        try {
            String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());
            return ResponseEntity.status(HttpStatus.CREATED).body(new CreateAccessTokenResponse(newAccessToken));
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
        
    }

    

    //유저 정보 조회
    @GetMapping("/me")
    public ResponseEntity<UserInformationResponse> getUserInfo(@AuthenticationPrincipal User user) {
        Long userId = user.getId();
        return ResponseEntity.ok(userService.getUserInfo(userId));
    }
}
