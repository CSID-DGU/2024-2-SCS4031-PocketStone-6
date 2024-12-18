package com.pocketstone.team_sync.event;

public class ProjectRegisteredEvent {
    private final Long companyId;
    private final Long projectId;

    public ProjectRegisteredEvent(Long companyId, Long projectId) {
        this.companyId = companyId;
        this.projectId = projectId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public Long getProjectId() {
        return projectId;
    }
}
