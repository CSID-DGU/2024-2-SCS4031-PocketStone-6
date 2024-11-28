package com.pocketstone.team_sync.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table
@NoArgsConstructor //(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Timeline {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "project_id", nullable = false)
    private Project project;

    @Column (name = "sprint_order", nullable = false)
    private Integer sprintOrder;

    @Column (name = "sprint_content", nullable = false)
    private String sprintContent;

    @Column (name = "sprint_startdate", nullable = false)
    private LocalDate sprintStartDate;

    @Column (name = "sprint_enddate", nullable = false)
    private LocalDate sprintEndDate;


    @Column (name = "requiredManmonth", scale = 2, nullable = false)
    private Double requiredManmonth;


    @Builder
    public Timeline(Project project, Integer sprintOrder, String sprintContent, LocalDate sprintStartDate, LocalDate sprintEndDate, Double requiredManmonth) {
        this.project = project;
        this.sprintOrder = sprintOrder;
        this.sprintContent = sprintContent;
        this.sprintStartDate = sprintStartDate;
        this.sprintEndDate = sprintEndDate;
        this.requiredManmonth = requiredManmonth;
    }
}
