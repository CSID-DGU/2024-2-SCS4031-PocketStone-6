package com.pocketstone.team_sync.controller;

import com.pocketstone.team_sync.dto.projectdto.*;
import com.pocketstone.team_sync.service.ManMonthService;
import com.pocketstone.team_sync.service.ProjectCharterService;
import com.pocketstone.team_sync.service.ProjectService;
import com.pocketstone.team_sync.service.TimelineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TimelineService timelineService;

    @Autowired
    private ProjectCharterService projectCharterService;

    @Autowired
    private ManMonthService manMonthService;

    //프로젝트 생성
    @PostMapping("/project")
    public ResponseEntity<ProjectDto> addProject(@RequestBody ProjectWrapperDto projectWrapperDto) {
        ProjectDto projectDto = projectWrapperDto.getProjectDto();
        return new ResponseEntity<>(projectService.save(projectDto), HttpStatus.CREATED);
    }


    //프로젝트 이름으로 프로젝트 찾기
    @GetMapping("/{projectName}")
    public ResponseEntity<ProjectDto> getProject(@PathVariable String projectName) {
        return projectService.findByProjectName(projectName)
                .map(project -> new ResponseEntity<>(project, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); //optional 값 없을때 404 반환
    }

    //모든 프로젝트 찾기
    @GetMapping("/all")
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        return new ResponseEntity<>(projectService.findAll(), HttpStatus.OK);
    }
    //@PostMapping 프로젝트 업데이트
    //@DeleteMapping 프로젝트 삭제
    //@GetMapping 프로젝트 시작일과 MVP일로 프로젝트 찾기

    //프로젝트 차터 생성
    @PostMapping("/{projectId}/charter")
    public ResponseEntity<ProjectCharterDto> saveProjectCharter(@PathVariable Long projectId,
                                                                @RequestBody ProjectCharterDto projectCharterDto) {
        return new ResponseEntity<>(projectCharterService.saveProjectCharter(projectId, projectCharterDto), HttpStatus.CREATED);

    }

    //프로젝트 id로 차터 검색
    @GetMapping("/{projectId}/charter")
    public ResponseEntity<ProjectCharterDto> getProjectCharter(@PathVariable Long projectId) {
        return new ResponseEntity<>(projectCharterService.findByProjectId(projectId), HttpStatus.OK);
    }

    //프로젝트 차터 업데이트
    @PutMapping("/{projectId}/charter")
    public ResponseEntity<ProjectCharterDto> updateProjectCharter(@PathVariable Long projectId,
                                                                  @RequestBody ProjectCharterDto projectCharterDto) {
        return new ResponseEntity<>(projectCharterService.updateProjectCharterByProjectId(projectId, projectCharterDto), HttpStatus.OK);
    }


    //프로젝트별 타임라인(스프린트)일정 추가
    @PostMapping("/{projectId}/timelines")
    public ResponseEntity<Void> addTimelines(@PathVariable Long projectId,
                                             @RequestBody List<TimelineDto> timelineDtos) {
        timelineService.saveTimelines(projectId, timelineDtos);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //프로젝트별 타임라인(스프린트)일정 조회
    @GetMapping("/{projectId}/timelines")
    public ResponseEntity<List<TimelineDto>> getAllTimelinesForProject(@PathVariable Long projectId) {
        List<TimelineDto> timelines = timelineService.findAllByProjectId(projectId);
        return new ResponseEntity<>(timelines, HttpStatus.OK);
    }

    //프로젝트 별 타임라인 업데이트 *업데이트 할때는 타임라인 table id도 같이 보내야함
    @PutMapping("/{projectId}/timelines")
    public ResponseEntity<List<TimelineDto>> updateTimelines(@PathVariable Long projectId,
                                                                   @RequestBody List<TimelineDto> timelineDtos) {
        List<TimelineDto> updatedTimelines = timelineService.updateTimelines(projectId, timelineDtos);

        if (!updatedTimelines.isEmpty()) {
            return new ResponseEntity<>(updatedTimelines, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //맨먼스 초기 설정
    @PostMapping("/{projectId}/{timeLineId}/man-month")
    public ResponseEntity<List<ManMonthDto>> saveManMonth(@PathVariable Long projectId,
                                                          @PathVariable Long timeLineId,
                                                          @RequestBody List<ManMonthDto> manMonthDtoList) {
        manMonthService.saveManMonth(projectId, timeLineId, manMonthDtoList);
        return new ResponseEntity<>(manMonthDtoList, HttpStatus.CREATED);
    }

    //세부 타임라인 별 맨먼스 조회
    @GetMapping("/{projectId}/{timeLineId}/man-month")
    public ResponseEntity<List<ManMonthDto>> getManMonth(@PathVariable Long projectId,
                                                         @PathVariable Long timeLineId) {
        return new ResponseEntity<>(manMonthService.findManMonthByProjectAndTimeline(projectId, timeLineId), HttpStatus.OK);
    }

    //프로젝트의 모든 맨먼스 조회
    @GetMapping("/{projectId}/man-month")
    public ResponseEntity<List<ManMonthDto>> getAllManMonth(@PathVariable Long projectId) {
        return new ResponseEntity<>(manMonthService.findManMonthByProject(projectId), HttpStatus.OK);
    }

    //맨먼스 업데이트
    @PutMapping("/{projectId}/{timeLineId}/man-month")
    public ResponseEntity<List<ManMonthDto>> updateManMonth(@PathVariable Long projectId,
                                                            @PathVariable Long timeLineId,
                                                            @RequestBody List<ManMonthDto> manMonthDtoList) {
        return new ResponseEntity<>(manMonthService.updateManMonth(projectId, timeLineId, manMonthDtoList), HttpStatus.OK);
    }
}
