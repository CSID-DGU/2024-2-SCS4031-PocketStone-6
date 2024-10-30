package com.pocketstone.team_sync.repository.charter;

import com.pocketstone.team_sync.entity.charter.Principle;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PrincipleRepository extends JpaRepository<Principle, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Principle p SET p.principleName = :principleName, p.principleContent = :principleContent WHERE p.projectCharter.project.id = :projectId AND p.id = :id")
    void updatePrincipleByProjectId(Long projectId, Long id, String principleName, String principleContent);
}
