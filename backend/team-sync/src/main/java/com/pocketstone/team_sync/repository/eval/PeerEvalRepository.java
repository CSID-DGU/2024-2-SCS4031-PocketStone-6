package com.pocketstone.team_sync.repository.eval;

import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.ProjectMember;
import com.pocketstone.team_sync.entity.evaluation.PeerEval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeerEvalRepository extends JpaRepository<PeerEval, Long> {
    Optional<PeerEval> findByProject(Project project);

    Optional<PeerEval> findByProjectAndProjectMember(Project project, ProjectMember projectMember);

    List<PeerEval> findAllByProject(Project project);
    void deleteByProject(Project project);
}