package com.pocketstone.team_sync.repository.charter;

import com.pocketstone.team_sync.entity.Charter.Position;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Position p SET p.positionName = :positionName, p.positionContent = :positionContent WHERE p.projectCharter.project.id = :projectId AND p.id = :id")
    void updatePositionByProjectId(Long projectId, Long id, String positionName, String positionContent);
}
