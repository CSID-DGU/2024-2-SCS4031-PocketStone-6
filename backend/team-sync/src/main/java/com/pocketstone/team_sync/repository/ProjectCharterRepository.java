package com.pocketstone.team_sync.repository;

import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.ProjectCharter;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
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
    @Query("UPDATE ProjectCharter p SET p = :projectCharter WHERE p.project.id = :projectId")//프로젝트 차터 테이블 업데이트 쿼리
    public void updateProjectCharterByProjectId(Long projectId, ProjectCharter projectCharter);
}

