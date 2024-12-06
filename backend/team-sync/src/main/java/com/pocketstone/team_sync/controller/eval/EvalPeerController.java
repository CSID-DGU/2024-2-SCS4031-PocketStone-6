package com.pocketstone.team_sync.controller.eval;

import com.pocketstone.team_sync.dto.eval.PeerEvalDto;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.service.ProjectEvalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/evaluations/peer")
@RequiredArgsConstructor
public class EvalPeerController {

    private final ProjectEvalService projectEvalService;

    @PostMapping
    public ResponseEntity<PeerEvalDto> createPeerEvaluation(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId,
            @RequestBody PeerEvalDto dto) {
        return ResponseEntity.ok(projectEvalService.savePeerEval(user, projectId, dto));
    }

    @GetMapping
    public ResponseEntity<List<PeerEvalDto>> getPeerEvaluation(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId) {
        return ResponseEntity.ok(projectEvalService.getPeerEval(user, projectId));
    }

    @PutMapping
    public ResponseEntity<PeerEvalDto> updatePeerEvaluation(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId,
            @RequestBody PeerEvalDto dto) {
        return ResponseEntity.ok(projectEvalService.updatePeerEval(user, projectId, dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePeerEvaluation(
            @AuthenticationPrincipal User user,
            @PathVariable Long projectId) {
        projectEvalService.deletePeerEval(user, projectId);
        return ResponseEntity.noContent().build();
    }
}