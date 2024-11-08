package com.pocketstone.team_sync.exception;

public class CharterDownloadException extends RuntimeException {
    public CharterDownloadException() {
        super("차터 PDF가 존재하지 않습니다.");
    }
}
