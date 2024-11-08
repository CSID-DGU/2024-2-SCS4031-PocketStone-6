package com.pocketstone.team_sync.exception;

public class CharterUploadException extends RuntimeException {
    public CharterUploadException(String message) {
        super("파일 업로드에 실패했습니다. [내용]: " + message );
    }
}
