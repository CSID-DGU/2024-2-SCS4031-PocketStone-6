package com.pocketstone.team_sync.repository.eval;

import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.charter.Objective;
import com.pocketstone.team_sync.entity.evaluation.ObjectiveAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ObjectiveAchievementRepository extends JpaRepository<ObjectiveAchievement, Long> {
    ObjectiveAchievement findByObjectiveId(Long objectiveId);

    Optional<ObjectiveAchievement> findByProject(Project project);

    void deleteByProject(Project project);

    Optional<ObjectiveAchievement> findByProjectAndObjective(Project project, Objective objective);

    List<ObjectiveAchievement> findAllByProject(Project project);
}