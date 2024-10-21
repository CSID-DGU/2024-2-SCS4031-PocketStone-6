package com.pocketstone.team_sync.repository;

import com.pocketstone.team_sync.entity.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimelineRepository extends JpaRepository<Timeline,Long> {
}
