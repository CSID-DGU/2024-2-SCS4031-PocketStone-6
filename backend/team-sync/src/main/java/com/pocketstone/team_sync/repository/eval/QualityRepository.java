package com.pocketstone.team_sync.repository.eval;

import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.evaluation.Quality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QualityRepository extends JpaRepository<Quality, Long> {
    Quality findByProjectId(Long projectId);
    Optional<Quality> findByProject(Project project);
    void deleteByProject(Project project);
}