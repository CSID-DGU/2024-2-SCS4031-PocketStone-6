package com.pocketstone.team_sync.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String loginId){
        super("해당 id의 정보를 찾을 수 없습니다: "+loginId);
    }
}
