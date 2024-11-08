package com.pocketstone.team_sync.exception;

public class CharterPdfNotFoundException extends RuntimeException {
    public CharterPdfNotFoundException() {
        super("차터 PDF가 존재하지 않습니다.");
    }
}
