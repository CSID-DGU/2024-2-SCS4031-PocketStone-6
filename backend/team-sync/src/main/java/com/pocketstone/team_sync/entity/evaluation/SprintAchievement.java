package com.pocketstone.team_sync.entity.evaluation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.Timeline;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@NoArgsConstructor
@Getter
@Setter
@Entity
public class SprintAchievement {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "project_id", nullable = false)
    private Project project;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "timeline_id", nullable = false)
    private Timeline timeline;

    @Column (name = "burndown_rate", nullable = false)
    private Double burndownRate;

}
