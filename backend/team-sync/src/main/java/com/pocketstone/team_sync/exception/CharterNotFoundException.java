package com.pocketstone.team_sync.exception;

public class CharterNotFoundException extends RuntimeException{
    public CharterNotFoundException() {
        super(  " 해당 프로젝트의 차터를 찾을 수 없습니다");
    }
}
