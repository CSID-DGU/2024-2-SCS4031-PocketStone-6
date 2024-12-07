package com.pocketstone.team_sync.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pocketstone.team_sync.entity.Employee;
import com.pocketstone.team_sync.entity.ManMonthAgg;

@Repository
public interface ManMonthAggRepository extends JpaRepository<ManMonthAgg, Long> {

    @Query("SELECT mma FROM ManMonthAgg mma " +
            "WHERE mma.employee = :employee " +
            "AND ((mma.weekStartDate BETWEEN :startDate AND :endDate) " +
            "OR (mma.weekEndDate BETWEEN :startDate AND :endDate) " +
            "OR (:startDate BETWEEN mma.weekStartDate AND mma.weekEndDate))")
    List<ManMonthAgg> findByEmployeeAndDateRange(
            @Param("employee") Employee employee,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    Optional<ManMonthAgg> findByEmployeeAndWeekStartDate(
            Employee employee,
            LocalDate weekStartDate
    );



}
