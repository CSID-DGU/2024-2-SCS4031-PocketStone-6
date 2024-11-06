package com.pocketstone.team_sync.exception;

public class CharterAlreadyExistsException extends RuntimeException{
    public CharterAlreadyExistsException() {
        super("프로젝트 차터가 이미 존재합니다");
    }
}
