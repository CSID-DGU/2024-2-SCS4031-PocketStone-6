package com.pocketstone.team_sync.exception;

public class CredentialsInvalidException extends RuntimeException {
    public CredentialsInvalidException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
