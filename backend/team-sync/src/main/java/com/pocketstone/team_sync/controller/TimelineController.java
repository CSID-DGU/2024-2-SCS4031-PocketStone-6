package com.pocketstone.team_sync.controller;

import com.pocketstone.team_sync.dto.projectdto.TimelineDto;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.service.TimelineService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@NoArgsConstructor
@RequestMapping("/api/projects/timelines")
public class TimelineController {

    @Autowired
    private TimelineService timelineService;

    //프로젝트별 타임라인(스프린트)일정 추가
    @PostMapping("/{projectId}")
    public ResponseEntity<Object> addTimelines(@AuthenticationPrincipal User user,
                                                          @PathVariable Long projectId,
                                                          @Valid @RequestBody List<TimelineDto> timelineDtos) {

            timelineService.saveTimelines(user, projectId, timelineDtos);
        return new ResponseEntity<>(timelineDtos, HttpStatus.CREATED);
    }

    //프로젝트별 타임라인(스프린트)일정 조회
    @GetMapping("/{projectId}")
    public ResponseEntity<List<TimelineDto>> getAllTimelinesForProject(@AuthenticationPrincipal User user,
                                                                       @PathVariable Long projectId) {
        List<TimelineDto> timelines = timelineService.findAllByProjectId(user, projectId);
        return new ResponseEntity<>(timelines, HttpStatus.OK);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Object> updateTimelines(@AuthenticationPrincipal User user,
                                                             @PathVariable Long projectId,
                                                             @RequestBody List<TimelineDto> timelineDtos) {

        List<TimelineDto> updatedTimelines = timelineService.updateTimelines(user, projectId, timelineDtos);

        return new ResponseEntity<>(updatedTimelines, HttpStatus.OK);

    }
}
