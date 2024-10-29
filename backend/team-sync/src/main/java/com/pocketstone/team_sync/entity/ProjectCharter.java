package com.pocketstone.team_sync.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pocketstone.team_sync.entity.charter.*;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
    public ProjectCharter(Project project) {
        this.project = project;
    }


}
