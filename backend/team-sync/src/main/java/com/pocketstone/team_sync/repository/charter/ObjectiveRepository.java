package com.pocketstone.team_sync.repository.charter;

import com.pocketstone.team_sync.entity.charter.Objective;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectiveRepository extends JpaRepository<Objective, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Objective o SET o.objectiveName = :objectiveName," +
            " o.objectiveContent = :objectiveContent " +
            "WHERE o.projectCharter.project.id = :projectId " +
            "AND o.id = :id")
    void updateObjectiveByProjectId(@Param("projectId") Long projectId,
                                    @Param("id") Long id,
                                    @Param("objectiveName") String objectiveName,
                                    @Param("objectiveContent") String objectiveContent);
}
