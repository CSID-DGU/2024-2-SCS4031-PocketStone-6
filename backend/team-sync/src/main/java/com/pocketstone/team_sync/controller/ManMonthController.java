package com.pocketstone.team_sync.controller;

import com.pocketstone.team_sync.dto.projectdto.ManMonthDto;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.service.ManMonthService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@NoArgsConstructor
@RequestMapping("/api/projects/man-month")
public class ManMonthController {

    @Autowired
    private ManMonthService manMonthService;


    //맨먼스 초기 설정
    @PostMapping("/{projectId}/{timeLineId}")
    public ResponseEntity<List<ManMonthDto>> saveManMonth(@AuthenticationPrincipal User user,
                                                          @PathVariable Long projectId,
                                                          @PathVariable Long timeLineId,
                                                          @RequestBody List<ManMonthDto> manMonthDtoList) {
        manMonthService.saveManMonth(user, projectId, timeLineId, manMonthDtoList);
        return new ResponseEntity<>(manMonthDtoList, HttpStatus.CREATED);
    }

    //세부 타임라인 별 맨먼스 조회
    @GetMapping("/{projectId}/{timeLineId}")
    public ResponseEntity<List<ManMonthDto>> getManMonth(@AuthenticationPrincipal User user,
                                                         @PathVariable Long projectId,
                                                         @PathVariable Long timeLineId) {
        return new ResponseEntity<>(manMonthService.findManMonthByProjectAndTimeline(user, projectId, timeLineId), HttpStatus.OK);
    }

    //프로젝트의 모든 맨먼스 조회
    @GetMapping("/{projectId}")
    public ResponseEntity<List<ManMonthDto>> getAllManMonth(@AuthenticationPrincipal User user,
                                                            @PathVariable Long projectId) {
        return new ResponseEntity<>(manMonthService.findManMonthByProject(user, projectId), HttpStatus.OK);
    }

    //맨먼스 업데이트
    @PutMapping("/{projectId}/{timeLineId}")
    public ResponseEntity<List<ManMonthDto>> updateManMonth(@AuthenticationPrincipal User user,
                                                            @PathVariable Long projectId,
                                                            @PathVariable Long timeLineId,
                                                            @RequestBody List<ManMonthDto> manMonthDtoList) {
        return new ResponseEntity<>(manMonthService.updateManMonth(user, projectId, timeLineId, manMonthDtoList), HttpStatus.OK);
    }
}
