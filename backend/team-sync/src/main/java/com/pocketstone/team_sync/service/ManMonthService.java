package com.pocketstone.team_sync.service;


import com.pocketstone.team_sync.dto.projectdto.ManMonthDto;
import com.pocketstone.team_sync.dto.projectdto.ManMonthUpdateDto;
import com.pocketstone.team_sync.entity.ManMonth;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.Timeline;
import com.pocketstone.team_sync.repository.ManMonthRepository;
import com.pocketstone.team_sync.repository.ProjectRepository;
import com.pocketstone.team_sync.repository.TimelineRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ManMonthService {

    @Autowired
    private ManMonthRepository manMonthRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TimelineRepository timelineRepository;

    //프로젝트의 타임라인 별로 맨먼스 저장
    public void saveManMonth(Long projectId, Long timeLineId, List<ManMonthDto> manMonthDtoList) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("프로젝트 찾을 수 없음"));
        Timeline timeline = timelineRepository.findById(timeLineId)
                .orElseThrow(() -> new RuntimeException("타임라인 찾을 수 없음"));

        for (ManMonthDto manMonthDto : manMonthDtoList) {
            manMonthRepository.save(
                    ManMonth.builder().project(project)
                            .timeline(timeline).position(manMonthDto.getPosition())
                            .manMonth(manMonthDto.getManMonth())
                            .build());
        }
    }

    //프로젝트의 타임라인 별로 맨먼스 조회
    public List<ManMonthUpdateDto> findManMonthByProjectAndTimeline(Long projectId, Long timelineId) {

        List<ManMonth> manMonths = manMonthRepository.findManMonthByProjectAndTimeline(projectId, timelineId);
        return manMonths.stream()
                .map(manMonth -> new ManMonthUpdateDto(
                        manMonth.getId(),
                        manMonth.getPosition(),
                        manMonth.getManMonth()
                ))
                .collect(Collectors.toList());
    }

    //프로젝트의 포지션별 맨먼스 총합 조회
    public List<ManMonthDto> findManMonthByProject(Long projectId) {
        List<ManMonth> manMonths = manMonthRepository.findManMonthByProject(projectId);

        //<포지션, 맨먼스>맵으로 포지션별 맨먼스 총합 계산
        Map<String, BigDecimal> manMonthByPosition = manMonths.stream()
                .collect(Collectors.groupingBy(ManMonth::getPosition,
                        Collectors.reducing(BigDecimal.ZERO, ManMonth::getManMonth, BigDecimal::add)
                ));

        //맵을 맨먼스dto로 변환
        return manMonthByPosition.entrySet().stream()
                .map(entry -> new ManMonthDto(
                        entry.getKey(),
                        entry.getValue()
                )).collect(Collectors.toList());

    }

    //맨먼스 지수 수정
    public List<ManMonthUpdateDto> updateManMonth(Long projectId, Long timelineId, List<ManMonthUpdateDto> manMonthDtos) {
        for (ManMonthUpdateDto manMonthDto : manMonthDtos) {
            manMonthRepository.updateManMonthByProjectAndTimeline(
                    manMonthDto.getId(),
                    projectId,
                    timelineId,
                    manMonthDto.getPosition(),
                    manMonthDto.getManMonth()
            );
        }
        return findManMonthByProjectAndTimeline(projectId, timelineId);
    }
}
