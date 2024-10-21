package com.pocketstone.team_sync.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor //(access = AccessLevel.PROTECTED)
@Table
public class SuccessCriteria {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "project_id", nullable = false)
    private Project project;

    @Column (name = "criteria_name", nullable = false)
    private String criteriaName;

    @Column (name = "criteria_description", nullable = false)
    private String criteriaDescription;

    @Column (name = "success_condition", nullable = false)
    private String successCondition;
}
