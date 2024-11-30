package com.pocketstone.team_sync.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pocketstone.team_sync.dto.projectdto.TimelineDto;
import com.pocketstone.team_sync.entity.Company;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.Timeline;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.exception.ProjectNotFoundException;
import com.pocketstone.team_sync.repository.CompanyRepository;
import com.pocketstone.team_sync.repository.ProjectRepository;
import com.pocketstone.team_sync.repository.TimelineRepository;
import com.pocketstone.team_sync.utility.ProjectValidationUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TimelineService {
    @Autowired
    private TimelineRepository timelineRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ProjectRepository projectRepository;


    public void saveTimelines(User user, Long projectId, List<TimelineDto> timelineDtos) {

        //전달 받은 프로젝트 아이디로 프로젝트 찾기
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(" "));
        ProjectValidationUtils.validateProjectOwner(company, project);


        for (TimelineDto timelineDto : timelineDtos) {
            timelineRepository.save(timelineDto.toTimeline(project, timelineDto));
        }
    }


    public List<TimelineDto> findAllByProjectId(User user, Long projectId) {

        //해당 프로젝트의 모든 타임라인 엔티티에서 조회
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        List<Timeline> timelines = timelineRepository.findAllByProjectId(projectId);
        ProjectValidationUtils.validateTimelineOwner(company, timelines.get(0));

        if (timelines.isEmpty()) {
            throw new ProjectNotFoundException(" ");
        }
        for (Timeline timeline : timelines) {
            ProjectValidationUtils.validateTimelineOwner(company, timeline);
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

        //타임라인Dto 리스트에 변동 된 타임라인 업데이트
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(" "));
        TimelineDto validateTimeline = timelineDtos.get(0);
        ProjectValidationUtils.validateTimelineOwner(company,
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
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        List<Timeline> timelines = timelineRepository.findAllByProjectId(projectId);
        for (Timeline timeline : timelines) {
            ProjectValidationUtils.validateTimelineOwner(company, timeline);
        }
        timelineRepository.deleteAllByProjectId(projectId);
    }
}
