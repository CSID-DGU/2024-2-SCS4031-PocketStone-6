package com.pocketstone.team_sync.exception;

public class CompanyNotFoundException extends RuntimeException {
    public CompanyNotFoundException(String userName) {
        super(userName+": 해당 유저가 소속된 회사를 찾을 수 없습u니다.");
    }
}
