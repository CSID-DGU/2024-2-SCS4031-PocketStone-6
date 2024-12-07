package com.pocketstone.team_sync.controller.eval;

import com.pocketstone.team_sync.dto.eval.QualityDto;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.service.ProjectEvalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/{projectId}/evaluations/quality")
@RequiredArgsConstructor
public class EvalQualityController {

    private final ProjectEvalService projectEvalService;

    @PostMapping
    public ResponseEntity<QualityDto> createQuality(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId,
            @RequestBody QualityDto dto) {
        return ResponseEntity.ok(projectEvalService.saveQuality(user, projectId, dto));
    }

    @GetMapping
    public ResponseEntity<QualityDto> getQuality(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId) {
        return ResponseEntity.ok(projectEvalService.getQuality(user, projectId));
    }

    @PutMapping
    public ResponseEntity<QualityDto> updateQuality(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId,
            @RequestBody QualityDto dto) {
        return ResponseEntity.ok(projectEvalService.updateQuality(user, projectId, dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteQuality(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId) {
        projectEvalService.deleteQuality(user, projectId);
        return ResponseEntity.noContent().build();
    }
}