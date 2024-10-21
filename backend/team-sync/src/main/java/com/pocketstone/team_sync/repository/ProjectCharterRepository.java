package com.pocketstone.team_sync.repository;

import com.pocketstone.team_sync.entity.ProjectCharter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectCharterRepository extends JpaRepository<ProjectCharter, Long> {
}
