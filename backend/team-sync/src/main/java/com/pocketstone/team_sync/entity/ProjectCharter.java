package com.pocketstone.team_sync.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pocketstone.team_sync.entity.charter.*;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table
@NoArgsConstructor//(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ProjectCharter {

    @Id
    @JsonIgnore
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    //프로젝트 id, 외래키
    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToMany(mappedBy = "projectCharter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Position> positions;

    @OneToMany(mappedBy = "projectCharter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Objective> objectives;

    @OneToMany(mappedBy = "projectCharter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Principle> principles;

    @OneToMany(mappedBy = "projectCharter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Risk> risks;

    @OneToMany(mappedBy = "projectCharter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Scope> scopes;

    @OneToMany(mappedBy = "projectCharter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stakeholder> stakeholders;

    @OneToMany(mappedBy = "projectCharter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vision> visions;


    @Builder
    public ProjectCharter(Project project, List<Position> positions, List<Objective> objectives, List<Principle> principles, List<Risk> risks, List<Scope> scopes, List<Stakeholder> stakeholders, List<Vision> visions) {

        this.project = project;
        this.positions = positions;
        this.objectives = objectives;
        this.principles = principles;
        this.risks = risks;
        this.scopes = scopes;
        this.stakeholders = stakeholders;
        this.visions = visions;


        if (objectives != null) objectives.forEach(objective -> objective.setProjectCharter(this));
        if (principles != null) principles.forEach(principle -> principle.setProjectCharter(this));
        if (risks != null) risks.forEach(risk -> risk.setProjectCharter(this));
        if (scopes != null) scopes.forEach(scope -> scope.setProjectCharter(this));
        if (stakeholders != null) stakeholders.forEach(stakeholder -> stakeholder.setProjectCharter(this));
        if (visions != null) visions.forEach(vision -> vision.setProjectCharter(this));
        if (positions != null) positions.forEach(position -> position.setProjectCharter(this));

    }


}
