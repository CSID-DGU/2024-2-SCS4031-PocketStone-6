package com.pocketstone.team_sync.exception;

public class LoginIdAlreadyExistsException extends RuntimeException{
    public LoginIdAlreadyExistsException (){
        super("이미 사용중인 id입니다");
    }

}
