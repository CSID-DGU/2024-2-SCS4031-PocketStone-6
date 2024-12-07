package com.pocketstone.team_sync.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.ProjectMember;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long>{

    Optional<ProjectMember> findByProjectIdAndEmployeeId(Long projectId, Long employeeId);
    //List<ProjectMember> findByProjectId(Long projectId);
    boolean existsByProjectId(Long projectId);
    void deleteAllByProjectId(Long projectId);
    @Query("SELECT pm FROM ProjectMember pm JOIN FETCH pm.employee WHERE pm.project.id = :projectId")
    List<ProjectMember> findByProjectIdWithEmployee(@Param("projectId") Long projectId);
    
    @Query("SELECT pm.project FROM ProjectMember pm WHERE pm.employee.id = :employeeId")
    List<Project> findProjectsByEmployeeId(@Param("employeeId") Long employeeId);
    List<ProjectMember> findAllByProjectId(Long projectId);
}

