package com.pocketstone.team_sync.exception;

public class ExceededWorkloadException extends RuntimeException {
    public ExceededWorkloadException(String employee, Double manMonth) {
        super(String.format(
                "직원 %s의 주간 업무량이 한도를 초과했습니다. 현재 가능 업무량 : %.2f .",
                employee, manMonth
        ));
    }
}
