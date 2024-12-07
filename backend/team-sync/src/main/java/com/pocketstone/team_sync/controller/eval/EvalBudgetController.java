package com.pocketstone.team_sync.controller.eval;

import com.pocketstone.team_sync.dto.eval.ResourcesUsageDto;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.service.ProjectEvalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/{projectId}/evaluations/resources")
@RequiredArgsConstructor
public class EvalBudgetController {

    private final ProjectEvalService projectEvalService;

    @PostMapping
    public ResponseEntity<ResourcesUsageDto> createResourcesUsage(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId,
            @RequestBody ResourcesUsageDto dto) {
        return ResponseEntity.ok(projectEvalService.saveResourcesUsage(user, projectId, dto));
    }

    @GetMapping
    public ResponseEntity<ResourcesUsageDto> getResourcesUsage(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId) {
        return ResponseEntity.ok(projectEvalService.getResourcesUsage(user, projectId));
    }

    @PutMapping
    public ResponseEntity<ResourcesUsageDto> updateResourcesUsage(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId,
            @RequestBody ResourcesUsageDto dto) {
        return ResponseEntity.ok(projectEvalService.updateResourcesUsage(user, projectId, dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteResourcesUsage(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId) {
        projectEvalService.deleteResourcesUsage(user, projectId);
        return ResponseEntity.noContent().build();
    }
}