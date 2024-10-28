package com.pocketstone.team_sync.exception;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException() {
        super("해당 프로젝트에 대한 권한이 없습니다.");
    }
}
