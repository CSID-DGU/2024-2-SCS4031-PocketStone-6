package com.pocketstone.team_sync.controller.eval;

import com.pocketstone.team_sync.dto.eval.SprintAchievementDto;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.service.ProjectEvalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/evaluations/sprints")
@RequiredArgsConstructor
public class EvalSprintController {

    private final ProjectEvalService projectEvalService;

    @PostMapping
    public ResponseEntity<SprintAchievementDto> createSprintAchievement(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId,
            @RequestBody SprintAchievementDto dto) {
        return ResponseEntity.ok(projectEvalService.saveSprintAchievement(user, projectId, dto));
    }

    @GetMapping
    public ResponseEntity <List<SprintAchievementDto>> getSprintAchievement(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId) {
        return ResponseEntity.ok(projectEvalService.getSprintAchievement(user, projectId));
    }

    @PutMapping
    public ResponseEntity<SprintAchievementDto> updateSprintAchievement(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId,
            @RequestBody SprintAchievementDto dto) {
        return ResponseEntity.ok(projectEvalService.updateSprintAchievement(user, projectId, dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSprintAchievement(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId) {
        projectEvalService.deleteSprintAchievement(user, projectId);
        return ResponseEntity.noContent().build();
    }
}