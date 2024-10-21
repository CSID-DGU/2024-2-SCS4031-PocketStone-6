package com.pocketstone.team_sync.repository;

import com.pocketstone.team_sync.entity.SuccessCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuccessCriteriaRepository extends JpaRepository<SuccessCriteria, Long> {
}
