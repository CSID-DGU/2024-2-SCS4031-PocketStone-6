package com.pocketstone.team_sync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pocketstone.team_sync.entity.Applicant;

public interface ApplicantRepository  extends JpaRepository<Applicant, Long> {

}
