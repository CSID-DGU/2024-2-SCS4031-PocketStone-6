package com.pocketstone.team_sync.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.ProjectTeammate;

public interface ProjectTeammateRepository extends JpaRepository<ProjectTeammate, Long>{

    ProjectTeammate findByProjectIdAndEmployeeId(Long projectId, Long employeeId);
    List<ProjectTeammate> findByProject(Project project);
}
