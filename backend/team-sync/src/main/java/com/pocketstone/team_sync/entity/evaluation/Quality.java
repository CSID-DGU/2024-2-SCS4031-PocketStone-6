package com.pocketstone.team_sync.entity.evaluation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pocketstone.team_sync.entity.Project;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Quality {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false)
    private Double testCoverage;

    @Column(nullable = false)
    private Double productQuality;

    @Column(nullable = false)
    private Integer foundBugs;

    @Column(nullable = false)
    private Double performance;

    @Column(nullable = false)
    private Double reliability;



}
