package com.pocketstone.team_sync.repository.charter;

import com.pocketstone.team_sync.entity.charter.Stakeholder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StakeholderRepository extends JpaRepository<Stakeholder, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Stakeholder s SET s.stakeholderName = :stakeholderName, s.stakeholderContent = :stakeholderContent WHERE s.projectCharter.project.id = :projectId AND s.id = :id")
    void updateStakeholderByProjectId(Long projectId, Long id, String stakeholderName, String stakeholderContent);
}
