package com.pocketstone.team_sync.controller;

import com.pocketstone.team_sync.dto.projectdto.TimelineDto;
import com.pocketstone.team_sync.service.TimelineService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@NoArgsConstructor
@RequestMapping("/api/projects/timelines")
public class TimelineController {

    @Autowired
    private TimelineService timelineService;

    //프로젝트별 타임라인(스프린트)일정 추가
    @PostMapping("/{projectId}")
    public ResponseEntity<Void> addTimelines(@PathVariable Long projectId,
                                             @RequestBody List<TimelineDto> timelineDtos) {
        timelineService.saveTimelines(projectId, timelineDtos);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //프로젝트별 타임라인(스프린트)일정 조회
    @GetMapping("/{projectId}")
    public ResponseEntity<List<TimelineDto>> getAllTimelinesForProject(@PathVariable Long projectId) {
        List<TimelineDto> timelines = timelineService.findAllByProjectId(projectId);
        return new ResponseEntity<>(timelines, HttpStatus.OK);
    }

    //프로젝트 별 타임라인 업데이트 *업데이트 할때는 타임라인 table id도 같이 보내야함
    @PutMapping("/{projectId}")
    public ResponseEntity<List<TimelineDto>> updateTimelines(@PathVariable Long projectId,
                                                             @RequestBody List<TimelineDto> timelineDtos) {
        List<TimelineDto> updatedTimelines = timelineService.updateTimelines(projectId, timelineDtos);

        if (!updatedTimelines.isEmpty()) {
            return new ResponseEntity<>(updatedTimelines, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
