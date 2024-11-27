package com.pocketstone.team_sync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pocketstone.team_sync.entity.Applicant;
import com.pocketstone.team_sync.entity.Company;

public interface ApplicantRepository  extends JpaRepository<Applicant, Long> {

    void deleteByCompany(Company company);
    void deleteByCompanyAndId(Company company, Long employeeId);
}
