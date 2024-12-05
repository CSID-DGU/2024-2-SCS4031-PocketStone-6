package com.pocketstone.team_sync.service;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import com.pocketstone.team_sync.entity.*;
import com.pocketstone.team_sync.repository.ManMonthAggRepository;
import org.springframework.stereotype.Service;

import com.pocketstone.team_sync.exception.ExceededWorkloadException;
import com.pocketstone.team_sync.repository.CompanyRepository;
import com.pocketstone.team_sync.repository.ManMonthRepository;
import com.pocketstone.team_sync.utility.ProjectValidationUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ManMonthService {

    private static final Double MAX_MANMONTH = 0.251;
    private final CompanyRepository companyRepository;
    private final ManMonthRepository manMonthRepository;
    private final ManMonthAggRepository manMonthAggRepository;

    public Map<LocalDate, Double> checkAvailability(Employee employee, LocalDate startDate, LocalDate endDate) {
        LocalDate startMonday = startDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endSunday = endDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        List<ManMonthAgg> existingAllocations = manMonthAggRepository.findByEmployeeAndDateRange(employee, startMonday, endSunday);

        Map<LocalDate, Double> weeklyAvailability = new HashMap<>();

        LocalDate currentWeek = startMonday;
        while (!currentWeek.isAfter(endSunday)) {
            weeklyAvailability.put(currentWeek, MAX_MANMONTH);
            currentWeek = currentWeek.plusWeeks(1);
        }

        for (ManMonthAgg allocation : existingAllocations) {
            weeklyAvailability.merge(
                    allocation.getWeekStartDate(),
                    -allocation.getManMonth(),
                    Double::sum
            );
        }

        return weeklyAvailability;

    }

    @Transactional
    public void allocateManmonth(User user, Employee employee, Project project, Timeline timeline, LocalDate startDate, LocalDate endDate, Map<LocalDate, Double> weeklyManmonths) {
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        ProjectValidationUtils.validateProjectOwner(company, project);
        Map<LocalDate, Double> availability = checkAvailability(employee, startDate, endDate);

        for (Map.Entry<LocalDate, Double> entry : weeklyManmonths.entrySet()) {
            LocalDate weekStart = entry.getKey();
            Double requiredManmonth = entry.getValue();
            Double availableManmonth = availability.getOrDefault(weekStart, MAX_MANMONTH);

            if (requiredManmonth > availableManmonth) {
               throw new ExceededWorkloadException(employee.getName(), availableManmonth);
            }

        }

        for (Map.Entry<LocalDate, Double> entry : weeklyManmonths.entrySet()) {
            allocateWork (employee, project, timeline, entry.getKey(), entry.getValue());
        }
    }

    private void allocateWork(Employee employee, Project project, Timeline timeline, LocalDate weekStartDate, Double manmonthValue) {

        LocalDate startMonday = weekStartDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endSunday = startMonday.plusDays(6);

        ManMonth allocation = manMonthRepository
                .findByEmployeeAndProjectAndTimelineAndWeekStartDate(employee, project, timeline, startMonday)
                .orElse(new ManMonth());

        if (allocation.getId() == null) {
            allocation.setEmployee(employee);
            allocation.setWeekStartDate(startMonday);
            allocation.setWeekEndDate(endSunday);
            allocation.setProject(project);
            allocation.setTimeline(timeline);
            allocation.setManMonth(manmonthValue);
        } else {
            allocation.setManMonth(allocation.getManMonth() + manmonthValue);
        }

        manMonthRepository.save(allocation);

        updateAggregateManMonth(employee, startMonday);

    }

    public void deleteManMonthsByProjectId(Long projectId) {
        List<ManMonth> manMonths = manMonthRepository.findByProjectId(projectId);

        if (!manMonths.isEmpty()) {
            Map<Employee, Set<LocalDate>> updateNeeded = new HashMap<>();

            for (ManMonth mm : manMonths) {
                updateNeeded
                        .computeIfAbsent(mm.getEmployee(), k -> new HashSet<>())
                        .add(mm.getWeekStartDate());
            }

            manMonthRepository.deleteAll(manMonths);
            updateNeeded.forEach((employee, weeks) ->
                    weeks.forEach(week ->
                            updateAggregateManMonth(employee, week)
                    )
            );
        }
    }

    public void updateAggregateManMonth(Employee employee, LocalDate weekStartDate) {
        Double totalManMonth = manMonthRepository.calculateTotalManMonthForWeek(employee, weekStartDate);

        ManMonthAgg aggregation = manMonthAggRepository
                .findByEmployeeAndWeekStartDate(employee, weekStartDate)
                .orElse(new ManMonthAgg(employee, weekStartDate, weekStartDate.plusDays(6), 0.0));

        aggregation.setManMonth(totalManMonth);

        manMonthAggRepository.save(aggregation);
    }

}
