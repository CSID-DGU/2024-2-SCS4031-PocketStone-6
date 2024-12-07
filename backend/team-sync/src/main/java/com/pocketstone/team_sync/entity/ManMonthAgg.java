package com.pocketstone.team_sync.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ManMonthAgg {


    @Id
    @JsonIgnore
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "employee_id", nullable = false)
    private Employee employee;

    @Column (name = "week_startdate", nullable = false)
    private LocalDate weekStartDate;

    @Column (name = "week_enddate", nullable = false)
    private LocalDate weekEndDate;

    @Column (name = "man_month", scale = 2, nullable = false)
    private Double manMonth;


    @Builder
    public ManMonthAgg(Employee employee, LocalDate weekStartDate, LocalDate weekEndDate, Double manMonth) {
        this.employee = employee;
        this.weekStartDate = weekStartDate;
        this.weekEndDate = weekEndDate;
        this.manMonth = manMonth;
    }


}
