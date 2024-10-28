package com.pocketstone.team_sync.repository.charter;

import com.pocketstone.team_sync.entity.Charter.Risk;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskRepository extends JpaRepository<Risk, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Risk r SET r.riskName = :riskName, r.riskContent = :riskContent WHERE r.projectCharter.project.id = :projectId AND r.id = :id")
    void updateRiskByProjectId(Long projectId, Long id, String riskName, String riskContent);
}
