package com.pocketstone.team_sync.service;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.pocketstone.team_sync.entity.Company;
import com.pocketstone.team_sync.entity.Employee;
import com.pocketstone.team_sync.entity.ManMonth;
import com.pocketstone.team_sync.entity.ManMonthAgg;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.Timeline;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.exception.ExceededWorkloadException;
import com.pocketstone.team_sync.repository.CompanyRepository;
import com.pocketstone.team_sync.repository.ManMonthAggRepository;
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
        List<ManMonth> allocationsToSave = new ArrayList<>();
        List<ManMonthAgg> aggregationsToUpdate = new ArrayList<>();

        for (Map.Entry<LocalDate, Double> entry : weeklyManmonths.entrySet()) {
            ManMonth allocation = createAllocation(employee, project, timeline,
                    entry.getKey(), entry.getValue());
            allocationsToSave.add(allocation);

            ManMonthAgg agg = prepareAggregateUpdate(employee, entry.getKey(), entry.getValue());
            aggregationsToUpdate.add(agg);
        }

        manMonthRepository.saveAll(allocationsToSave);
        manMonthAggRepository.saveAll(aggregationsToUpdate);

    }

    private ManMonth createAllocation(Employee employee, Project project, Timeline timeline,
                                      LocalDate weekStartDate, Double manmonthValue) {
        LocalDate startMonday = weekStartDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endSunday = startMonday.plusDays(6);

        return manMonthRepository
                .findByEmployeeAndProjectAndTimelineAndWeekStartDate(
                        employee, project, timeline, startMonday)
                .map(existing -> {
                    existing.setManMonth(existing.getManMonth() + manmonthValue);
                    return existing;
                })
                .orElseGet(() -> {
                    ManMonth newAllocation = new ManMonth();
                    newAllocation.setEmployee(employee);
                    newAllocation.setWeekStartDate(startMonday);
                    newAllocation.setWeekEndDate(endSunday);
                    newAllocation.setProject(project);
                    newAllocation.setTimeline(timeline);
                    newAllocation.setManMonth(manmonthValue);
                    return newAllocation;
                });
    }

    private ManMonthAgg prepareAggregateUpdate(Employee employee, LocalDate weekStartDate,
                                               Double additionalManMonth) {
        Double totalManMonth = manMonthRepository.calculateTotalManMonthForWeek(employee, weekStartDate);
        double currentTotal = (totalManMonth != null ? totalManMonth : 0.0);

        return manMonthAggRepository
                .findByEmployeeAndWeekStartDate(employee, weekStartDate)
                .map(existing -> {
                    existing.setManMonth(currentTotal + additionalManMonth);
                    return existing;
                })
                .orElseGet(() -> new ManMonthAgg(
                        employee,
                        weekStartDate,
                        weekStartDate.plusDays(6),
                        additionalManMonth
                ));
    }


    @Transactional
    public void deleteManMonthsByProjectId(Long projectId) {
        List<ManMonth> manMonths = manMonthRepository.findByProjectId(projectId);

        if (!manMonths.isEmpty()) {
            for (ManMonth mm : manMonths) {
                subtractAggregateManMonth(
                        mm.getEmployee(),
                        mm.getWeekStartDate(),
                        mm.getManMonth()
                );
            }

            manMonthRepository.deleteAll(manMonths);
        }
        }



    public void subtractAggregateManMonth(Employee employee, LocalDate weekStartDate, Double manMonthValue) {
        ManMonthAgg aggregation = manMonthAggRepository
                .findByEmployeeAndWeekStartDate(employee, weekStartDate)
                .orElse(null);

        if (aggregation != null) {
            aggregation.setManMonth(Math.max(0.0, aggregation.getManMonth() - manMonthValue));
            manMonthAggRepository.save(aggregation);
        }
    }


}
