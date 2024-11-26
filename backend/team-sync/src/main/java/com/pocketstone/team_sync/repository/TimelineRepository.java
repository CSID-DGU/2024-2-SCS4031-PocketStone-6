package com.pocketstone.team_sync.repository;

import com.pocketstone.team_sync.dto.projectdto.TimelineDto;
import com.pocketstone.team_sync.entity.Timeline;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimelineRepository extends JpaRepository<Timeline,Long> {
    List<Timeline> findAllByProjectId(Long projectId);

    @Modifying
    @Transactional
    @Query("UPDATE Timeline t SET t.sprintContent = :sprintContent, " +
            "t.sprintStartDate = :sprintStartDate, " +
            "t.sprintEndDate = :sprintEndDate, " +
            "t.requiredManmonth = :requiredManmonth, " +
            "t.sprintOrder = :sprintOrder WHERE t.project.id = :projectId AND t.id = :id") //타임라인 테이블 업데이트 쿼리
    public void updateTimelineByProjectId(Long projectId,
                                          Long id,
                                          String sprintContent,
                                          Integer sprintOrder,
                                          LocalDate sprintStartDate,
                                          LocalDate sprintEndDate,
                                          Double requiredManmonth);


}
