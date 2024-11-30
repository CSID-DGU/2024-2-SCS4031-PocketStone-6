package com.pocketstone.team_sync.utility;

import com.pocketstone.team_sync.entity.Company;
import com.pocketstone.team_sync.entity.ManMonth;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.ProjectCharter;
import com.pocketstone.team_sync.entity.Timeline;
import com.pocketstone.team_sync.exception.UnauthorizedAccessException;


public class ProjectValidationUtils {

    public static void validateProjectOwner(Company company, Project project) {
        if (!project.getCompany().getId().equals(company.getId())) {
            throw new UnauthorizedAccessException();
        }
    }

    public static void validateCharterOwner(Company company, ProjectCharter projectcharter) {
        if (!projectcharter.getProject().getCompany().getId().equals(company.getId())) {
            throw new UnauthorizedAccessException();
        }
    }

    public static void validateTimelineOwner(Company company, Timeline timeline) {
        if (!timeline.getProject().getCompany().getId().equals(company.getId())) {
            throw new UnauthorizedAccessException();
        }
    }

}
