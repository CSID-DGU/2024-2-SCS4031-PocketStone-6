package com.pocketstone.team_sync.repository;

import com.pocketstone.team_sync.dto.projectdto.ManMonthDto;
import com.pocketstone.team_sync.entity.ManMonth;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ManMonthRepository extends JpaRepository<ManMonth, Long> {


    @Query("SELECT m FROM ManMonth m WHERE m.project.id = :projectId AND m.timeline.id = :timelineId")
    public List<ManMonth> findManMonthByProjectAndTimeline(Long projectId, Long timelineId);

    @Query("SELECT m FROM ManMonth m WHERE m.project.id = :projectId")
    public List<ManMonth> findManMonthByProject(Long projectId);

    @Transactional
    @Modifying
    @Query ("UPDATE ManMonth t SET " +
            "t.position = :position, " +
            "t.manMonth = :manMonth WHERE t.id = :id AND t.project.id = :projectId AND t.timeline.id = :timelineId")
    public void updateManMonthByProjectAndTimeline(Long id, Long projectId, Long timelineId, String position, BigDecimal manMonth);

}
