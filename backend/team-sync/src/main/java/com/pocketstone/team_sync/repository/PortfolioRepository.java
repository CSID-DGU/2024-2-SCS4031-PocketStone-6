package com.pocketstone.team_sync.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pocketstone.team_sync.entity.Portfolio;

public interface PortfolioRepository  extends JpaRepository<Portfolio, Long> {

}
