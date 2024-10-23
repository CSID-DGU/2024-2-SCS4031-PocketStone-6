package com.pocketstone.team_sync.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pocketstone.team_sync.dto.projectdto.TimelineDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column (name = "sprint_duration_week", nullable = false)
    private Integer sprintDurationWeek;


    @Builder
    public Timeline(Project project, Integer sprintOrder, String sprintContent, Integer sprintDurationWeek) {
        this.project = project;
        this.sprintOrder = sprintOrder;
        this.sprintContent = sprintContent;
        this.sprintDurationWeek = sprintDurationWeek;
    }
}
