package com.pocketstone.team_sync.repository.eval;

import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.ProjectMember;
import com.pocketstone.team_sync.entity.evaluation.SprintAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SprintAchievementRepository extends JpaRepository<SprintAchievement, Long> {


    List<SprintAchievement> findAllByProjectMember(ProjectMember member);

    Optional<SprintAchievement> findByTimelineIdAndProjectMember(Long timelineId, ProjectMember member);

    void deleteAllByProjectAndProjectMember(Project project, ProjectMember member);
}