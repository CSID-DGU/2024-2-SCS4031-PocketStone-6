package com.pocketstone.team_sync.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class UserApiController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    //아이디 중복확인
    @PostMapping("/signup/check-loginid")
    public ResponseEntity<MessageResponse> checkLoginId(@RequestBody CheckLoginIdRequest inputLoginId) {
        if(!userRepository.existsByLoginId(inputLoginId.getLoginId())){
            return ResponseEntity.ok(new MessageResponse("사용가능한 아이디입니다."));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("이미 사용 중인 아이디입니다."));
    }

    //이메일 중복확인
    @PostMapping("/signup/check-email")
    public ResponseEntity<MessageResponse> checkEmail(@RequestBody CheckEmailRequest inputEmail) {
        if(!userRepository.existsByEmail(inputEmail.getEmail())){
            return ResponseEntity.ok(new MessageResponse("사용가능한 이메일입니다."));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("이미 등록된 이메일입니다."));
    
    }


    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody AddUserRequest request) {
        userService.save(request);
        return ResponseEntity.ok(new MessageResponse("가입이 성공적으로 완료되었습니다."));
    }

    //회원탈퇴
    @DeleteMapping("/api/withdraw")
    public ResponseEntity<MessageResponse> deleteAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByLoginId(authentication.getName()).orElse(null);
        Long userId = user.getId();
        userService.deleteAccount(userId);
        return ResponseEntity.ok(new MessageResponse("탈퇴처리 되었습니다."));
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<LoginTokenResponse> login(@RequestBody LoginRequest loginRequest) {
        String loginId = loginRequest.getLoginId();
        
        String password = loginRequest.getPassword();
        //System.out.println("이게뭐야야야야야"+loginId+password);
        // LoginTokenResponse loginToken = userService.login(loginId, password);
        // return loginToken;
        //try {
        // LoginTokenResponse loginToken = userService.login(loginId, password);
        // if (loginToken != null) {
        //     // 로그인 성공 시 토큰과 함께 200 OK 응답
        //     return ResponseEntity.ok(loginToken);
        //  } //에러 처리 다시하기 
         //else {
        //     // 로그인 실패 시 401 Unauthorized 응답
        //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호를 다시 확인해주세요.");
        // }
        // } catch (Exception e) {
        //     // 예외 발생 시 400 Bad Request 응답
        //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred");
        //}

        LoginTokenResponse loginToken = userService.login(loginId, password);
        
        
        return ResponseEntity.ok(loginToken);
        
    }

    
    //토큰 재요청
    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateAccessTokenResponse(newAccessToken));
    }
    // //로그아웃
    // @PostMapping("/logout")
    // public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
    //     // "Bearer " 접두사 제거
    //     String token = authorizationHeader.substring(7);
    //     tokenService.blacklistToken(token);
    //     return ResponseEntity.ok("로그아웃 되었습니다.");
    // }

    //유저 정보 조회
    @GetMapping("/api/user")
    public ResponseEntity<UserInformationResponse> getUserInfo(@AuthenticationPrincipal UserDetails userDetail) {
        
        String loginId = userDetail.getUsername();
        return ResponseEntity.ok(userService.getUserInfo(loginId));
    }
}
