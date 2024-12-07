package com.pocketstone.team_sync.repository.eval;

import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.evaluation.ResourcesUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourcesUsageRepository extends JpaRepository<ResourcesUsage, Long> {
    ResourcesUsage findByProjectId(Long projectId);
    Optional<ResourcesUsage> findByProject(Project project);
    void deleteByProject(Project project);
}
