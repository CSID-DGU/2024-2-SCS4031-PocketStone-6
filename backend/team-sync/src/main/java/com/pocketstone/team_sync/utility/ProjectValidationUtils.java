package com.pocketstone.team_sync.utility;

import com.pocketstone.team_sync.entity.*;
import com.pocketstone.team_sync.exception.UnauthorizedAccessException;


public class ProjectValidationUtils {

    public static void validateProjectOwner(User user, Project project) {
        if (!project.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException();
        }
    }

    public static void validateCharterOwner(User user, ProjectCharter projectcharter) {
        if (!projectcharter.getProject().getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException();
        }
    }

    public static void validateTimelineOwner(User user, Timeline timeline) {
        if (!timeline.getProject().getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException();
        }
    }

    public static void validateManmonthOwner(User user, ManMonth manmonth) {
        if (!manmonth.getProject().getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException();
        }
    }
}
