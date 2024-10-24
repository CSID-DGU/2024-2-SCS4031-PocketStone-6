package com.pocketstone.team_sync.repository;

import com.pocketstone.team_sync.entity.ProjectCharter;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectCharterRepository extends JpaRepository<ProjectCharter, Long> {

    public Optional<ProjectCharter> findByProjectId(Long projectId);

    @Modifying
    @Transactional
    @Query("UPDATE ProjectCharter t SET t.teamPositions = :teamPositions, " +
            "t.vision = :vision, " +
            "t.teamPositions = :teamPositions, " +
            "t.objective = :objective, " +
            "t.stakeholder = :stakeholder, " +
            "t.scope = :scope, " +
            "t.risk = :risk, " +
            "t.principle = :principle WHERE t.project.id = :projectId") //프로젝트 차터 테이블 업데이트 쿼리
    public void updateProjectCharterByProjectId(Long projectId,
                                                String teamPositions,
                                                String vision,
                                                String objective,
                                                String stakeholder,
                                                String scope,
                                                String risk,
                                                String principle);
}

