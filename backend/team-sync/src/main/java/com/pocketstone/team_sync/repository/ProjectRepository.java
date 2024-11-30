package com.pocketstone.team_sync.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pocketstone.team_sync.entity.Company;
import com.pocketstone.team_sync.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByProjectNameAndCompany(String projectName, Company company);

    List<Project> findAllByCompany (Company company);

    Optional<Project> findByCompanyAndId(Company company, Long projectId);//동시 조회
    boolean existsByCompanyIdAndId(Long companyId, Long projectId);

}
