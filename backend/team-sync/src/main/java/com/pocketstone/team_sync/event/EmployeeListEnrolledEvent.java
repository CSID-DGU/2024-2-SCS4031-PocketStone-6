package com.pocketstone.team_sync.event;

import org.springframework.context.ApplicationEvent;

public class EmployeeListEnrolledEvent extends ApplicationEvent{

    private final Long companyId;

    public EmployeeListEnrolledEvent(Object source, Long companyId) {
        super(source);
        this.companyId = companyId;
    }

    public Long getCompanyId() {
        return companyId;
    }
}
