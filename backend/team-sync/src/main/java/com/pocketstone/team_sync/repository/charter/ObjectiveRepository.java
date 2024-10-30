package com.pocketstone.team_sync.repository.charter;

import com.pocketstone.team_sync.entity.charter.Objective;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectiveRepository extends JpaRepository<Objective, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Objective o SET o.objectiveName = :objectiveName, o.objectiveContent = :objectiveContent WHERE o.projectCharter.project.id = :projectId AND o.id = :id")
    public void updateObjectiveByProjectId(Long projectId, Long id, String objectiveName, String objectiveContent);
}
