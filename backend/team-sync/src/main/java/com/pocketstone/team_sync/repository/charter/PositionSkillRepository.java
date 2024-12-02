package com.pocketstone.team_sync.repository.charter;

import com.pocketstone.team_sync.entity.charter.PositionSkill;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface PositionSkillRepository extends JpaRepository<PositionSkill, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE PositionSkill ps SET ps.skill = :skill WHERE ps.position.id = :positionId")
    void updateSkillByPositionId(Long positionId, Set<PositionSkill> skill);
}
