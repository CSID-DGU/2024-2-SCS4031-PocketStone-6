package com.pocketstone.team_sync.exception;

public class CharterPdfAlreadyExistsException extends RuntimeException {
    public CharterPdfAlreadyExistsException() {
        super("차터 PDF가 이미 존재합니다.");
    }
}
