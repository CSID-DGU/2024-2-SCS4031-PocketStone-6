package com.pocketstone.team_sync.service;

import com.pocketstone.team_sync.dto.projectdto.TimelineDto;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.Timeline;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.exception.ProjectNotFoundException;
import com.pocketstone.team_sync.repository.ProjectRepository;
import com.pocketstone.team_sync.repository.TimelineRepository;
import com.pocketstone.team_sync.utility.ProjectValidationUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TimelineService {
    @Autowired
    private TimelineRepository timelineRepository;

    @Autowired
    private ProjectRepository projectRepository;


    public void saveTimelines(User user, Long projectId, List<TimelineDto> timelineDtos) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(" "));
        ProjectValidationUtils.validateProjectOwner(user, project);


        for (TimelineDto timelineDto : timelineDtos) {
            timelineRepository.save(timelineDto.toTimeline(project, timelineDto));
        }
    }


    public List<TimelineDto> findAllByProjectId(User user, Long projectId) {

        List<Timeline> timelines = timelineRepository.findAllByProjectId(projectId);
        if (timelines.isEmpty()) {
            throw new ProjectNotFoundException(" ");
        }
        for (Timeline timeline : timelines) {
            ProjectValidationUtils.validateTimelineOwner(user, timeline);
        }


        return timelines.stream()
                .map(timeline -> new TimelineDto(
                        timeline.getId(),
                        timeline.getSprintOrder(),
                        timeline.getSprintContent(),
                        timeline.getSprintStartDate(),
                        timeline.getSprintEndDate(),
                        timeline.getRequiredManmonth()
                ))
                .collect(Collectors.toList());
    }


    public List<TimelineDto> updateTimelines(User user, Long projectId, List<TimelineDto> timelineDtos) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(" "));
        TimelineDto validateTimeline = timelineDtos.get(0);
        ProjectValidationUtils.validateTimelineOwner(user,
                validateTimeline.toTimeline(project, validateTimeline));
        for (TimelineDto timelineDto : timelineDtos) {
            timelineRepository.updateTimelineByProjectId(
                    projectId,
                    timelineDto.getId(),
                    timelineDto.getSprintContent(),
                    timelineDto.getSprintOrder(),
                    timelineDto.getSprintStartDate(),
                    timelineDto.getSprintEndDate(),
                    timelineDto.getRequiredManmonth());
        }
        return timelineDtos;
    }

    public void deleteTimelines(User user, Long projectId) {
        List<Timeline> timelines = timelineRepository.findAllByProjectId(projectId);
        for (Timeline timeline : timelines) {
            ProjectValidationUtils.validateTimelineOwner(user, timeline);
        }
        timelineRepository.deleteAllByProjectId(projectId);
    }
}
