package com.pocketstone.team_sync.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table
@NoArgsConstructor //(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class ManMonth {


    @Id
    @JsonIgnore
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "project_id", nullable = false)
    private Project project;


    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "timeline_id", nullable = false)
    private Timeline timeline;


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
    public ManMonth(Project project, Timeline timeline, Employee employee, LocalDate weekStartDate, LocalDate weekEndDate, Double manMonth) {
        this.project = project;
        this.timeline = timeline;
        this.employee = employee;
        this.weekStartDate = weekStartDate;
        this.weekEndDate = weekEndDate;
        this.manMonth = manMonth;
    }


}
