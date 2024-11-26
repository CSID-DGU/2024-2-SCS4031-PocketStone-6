package com.pocketstone.team_sync.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("이미 사용중인 email입니다.");
    }
}
