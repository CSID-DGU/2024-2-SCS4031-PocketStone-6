package com.pocketstone.team_sync.service;

import com.pocketstone.team_sync.dto.projectdto.TimelineDto;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.Timeline;
import com.pocketstone.team_sync.repository.ProjectRepository;
import com.pocketstone.team_sync.repository.TimelineRepository;
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

    // 프로젝트별 타임라인(스프린트)일정 추가
    public void saveTimelines(Long projectId, List<TimelineDto> timelineDtos) {
        //전달 받은 프로젝트 아이디로 프로젝트 찾기
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("프로젝트 찾을 수 없음"));

        //각 타임라인dto를 타임라인 엔티티로 저장
        for (TimelineDto timelineDto : timelineDtos) {
            timelineRepository.save(timelineDto.toTimeline(project, timelineDto));
        }
    }

    //프로젝트 타임라인 프로젝트 id로 조회
    public List<TimelineDto> findAllByProjectId(Long projectId) {
        //해당 프로젝트의 모든 타임라인 엔티티에서 조회
        List<Timeline> timelines = timelineRepository.findAllByProjectId(projectId);

        //dto로 반환
        return timelines.stream()
                .map(timeline -> new TimelineDto(
                        timeline.getId(),
                        timeline.getSprintOrder(),
                        timeline.getSprintContent(),
                        timeline.getSprintDurationWeek()
                ))
                .collect(Collectors.toList());
    }

    //프로젝트 타임라인 업데이트
    public List<TimelineDto> updateTimelines(Long projectId, List<TimelineDto> timelineDtos) {
        //타임라인Dto 리스트에 변동 된 타임라인 업데이트
        for (TimelineDto timelineDto : timelineDtos) {
            timelineRepository.updateTimelineByProjectId(
                    projectId,
                    timelineDto.getId(),
                    timelineDto.getSprintContent(),
                    timelineDto.getSprintDurationWeek(),
                    timelineDto.getSprintOrder());
        }
        return findAllByProjectId (projectId);
    }
}
