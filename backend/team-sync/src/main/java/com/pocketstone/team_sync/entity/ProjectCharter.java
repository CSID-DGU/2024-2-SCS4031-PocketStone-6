package com.pocketstone.team_sync.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
@Getter

public class ProjectCharter {

    @Id
    @JsonIgnore
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    //프로젝트 id, 외래키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    //필요한 포지션들
    @Column(name = "team_positions", nullable = false)
    private String teamPositions;

    //프로젝트 비션
    @Column (name = "vision", nullable = false)
    private String vision;

    //프로젝트 목표
    @Column (name = "objective", nullable = false)
    private String objective;

    //프로젝트 이해관계자
    @Column (name = "stakeholder", nullable = false)
    private String stakeholder;

    //프로젝트 범위
    @Column (name = "scope", nullable = false)
    private String scope;

    //프로젝트 위험
    @Column (name = "risk", nullable = false)
    private String risk;

    //프로젝트 원칙
    @Column (name = "principle", nullable = false)
    private String principle;

}
