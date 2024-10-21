package com.pocketstone.team_sync.repository;

import com.pocketstone.team_sync.entity.ManMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManMonthRepository extends JpaRepository<ManMonth, Long> {
}
