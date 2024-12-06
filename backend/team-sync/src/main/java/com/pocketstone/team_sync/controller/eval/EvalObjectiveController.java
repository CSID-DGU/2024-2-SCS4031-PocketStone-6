package com.pocketstone.team_sync.controller.eval;

import com.pocketstone.team_sync.dto.eval.ObjectiveAchievementDto;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.service.ProjectEvalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/{projectId}/evaluations/objective")
@RequiredArgsConstructor
public class EvalObjectiveController {

    private final ProjectEvalService projectEvalService;

    @PostMapping
    public ResponseEntity<ObjectiveAchievementDto> createObjectiveAchievement(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId,
            @RequestBody ObjectiveAchievementDto dto) {
        return ResponseEntity.ok(projectEvalService.saveObjectiveAchievement(user, projectId, dto));
    }

    @GetMapping
    public ResponseEntity<ObjectiveAchievementDto> getObjectiveAchievement(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId) {
        return ResponseEntity.ok(projectEvalService.getObjectiveAchievement(user, projectId));
    }

    @PutMapping
    public ResponseEntity<ObjectiveAchievementDto> updateObjectiveAchievement(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId,
            @RequestBody ObjectiveAchievementDto dto) {
        return ResponseEntity.ok(projectEvalService.updateObjectiveAchievement(user, projectId, dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteObjectiveAchievement(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId) {
        projectEvalService.deleteObjectiveAchievement(user, projectId);
        return ResponseEntity.noContent().build();
    }
}