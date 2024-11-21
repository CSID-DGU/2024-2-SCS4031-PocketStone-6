package com.pocketstone.team_sync.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pocketstone.team_sync.entity.ProjectMember;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long>{

    Optional<ProjectMember> findByProjectIdAndEmployeeId(Long projectId, Long employeeId);
    List<ProjectMember> findByProjectId(Long projectId);
    boolean existsByProjectId(Long projectId);
    void deleteAllByProjectId(Long projectId);

}
