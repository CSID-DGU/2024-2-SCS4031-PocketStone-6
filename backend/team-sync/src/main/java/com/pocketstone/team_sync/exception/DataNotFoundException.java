package com.pocketstone.team_sync.exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException() {
        super("해당 자료를 찾을 수 없습니다");
    }
}
