package com.pocketstone.team_sync.controller;


import com.pocketstone.team_sync.dto.projectdto.ProjectCharterDto;
import com.pocketstone.team_sync.service.ProjectCharterService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@NoArgsConstructor
@RequestMapping("/api/projects/charter")
public class CharterController {

    @Autowired
    private ProjectCharterService projectCharterService;

    //프로젝트 차터 저장
    @PostMapping("/{projectId}")
    public ResponseEntity<ProjectCharterDto> saveProjectCharter(@PathVariable Long projectId,
                                                                @RequestBody ProjectCharterDto projectCharterDto) {
        return new ResponseEntity<>(projectCharterService.saveProjectCharter(projectId, projectCharterDto), HttpStatus.CREATED);

    }

    //프로젝트 id로 차터 검색
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectCharterDto> getProjectCharter(@PathVariable Long projectId) {
        return new ResponseEntity<>(projectCharterService.findByProjectId(projectId), HttpStatus.OK);
    }

    //프로젝트 차터 업데이트
    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectCharterDto> updateProjectCharter(@PathVariable Long projectId,
                                                                  @RequestBody ProjectCharterDto projectCharterDto) {
        return new ResponseEntity<>(projectCharterService.updateProjectCharterByProjectId(projectId, projectCharterDto), HttpStatus.OK);
    }
}
