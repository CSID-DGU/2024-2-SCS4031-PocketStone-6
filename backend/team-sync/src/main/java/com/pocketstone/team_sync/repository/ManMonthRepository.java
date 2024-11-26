package com.pocketstone.team_sync.repository;

import com.pocketstone.team_sync.entity.Employee;
import com.pocketstone.team_sync.entity.ManMonth;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ManMonthRepository extends JpaRepository<ManMonth, Long> {

    @Query("SELECT mm FROM ManMonth mm " +
            "WHERE mm.employee = :employee " +
            "AND ((mm.weekStartDate BETWEEN :startDate AND :endDate) " +
            "OR (mm.weekEndDate BETWEEN :startDate AND :endDate) " +
            "OR (:startDate BETWEEN mm.weekStartDate AND mm.weekEndDate))")
    List<ManMonth> findByEmployeeAndDateRange(
            @Param("employee") Employee employee,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    Optional<ManMonth> findByEmployeeAndWeekStartDate(
            Employee employee,
            LocalDate weekStartDate
    );

    List<ManMonth> findByProject(Project project);

    List<ManMonth> findByTimeline(Timeline timeline);

    List<ManMonth> findByEmployeeAndProject(Employee employee, Project project);
}
