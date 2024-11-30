package com.pocketstone.team_sync.exception;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(Long usezrId) {
        super(" 회사를 찾을 수 없습u니다.");
    }
}
