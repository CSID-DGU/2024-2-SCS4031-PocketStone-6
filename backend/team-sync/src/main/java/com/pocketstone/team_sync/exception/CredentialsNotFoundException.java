package com.pocketstone.team_sync.exception;

public class CredentialsNotFoundException extends RuntimeException {
    public CredentialsNotFoundException(String loginId) {
        super(loginId + ": 해당 id의 계정을 찾을 수 없습니다.");
    }
}
