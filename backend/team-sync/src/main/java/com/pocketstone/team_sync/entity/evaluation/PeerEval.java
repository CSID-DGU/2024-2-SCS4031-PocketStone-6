package com.pocketstone.team_sync.entity.evaluation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.ProjectMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@NoArgsConstructor
@Getter
@Setter
@Entity
public class PeerEval {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "project_id", nullable = false)
    private Project project;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "projectMember_id", nullable = false)
    private ProjectMember projectMember;

    @Column(nullable = false)
    private Double performanceScore;  // 업무 수행 역량

    @Column(nullable = false)
    private Double interpersonalScore;    // 대인 관계 능력

    @Column(nullable = false)
    private Double expertiseScore;        // 전문성

    @Column(nullable = false)
    private Double responsibilityScore;  //책임감



}
