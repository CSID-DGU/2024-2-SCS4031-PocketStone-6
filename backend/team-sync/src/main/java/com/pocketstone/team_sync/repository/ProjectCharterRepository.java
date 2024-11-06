package com.pocketstone.team_sync.repository;

import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.ProjectCharter;
import com.pocketstone.team_sync.entity.charter.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectCharterRepository extends JpaRepository<ProjectCharter, Long> {

    public Optional<ProjectCharter> findByProjectId(Long projectId);

    @Modifying
    @Transactional
    @Query("UPDATE ProjectCharter p SET p.objectives = :objectives WHERE p.project.id = :projectId")
    void updateObjectives(Long projectId, List<Objective> objectives);

    @Modifying
    @Transactional
    @Query("UPDATE ProjectCharter p SET p.positions = :positions WHERE p.project.id = :projectId")
    void updatePositions(Long projectId, List<Position> positions);
}

