package com.pocketstone.team_sync.entity.charter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pocketstone.team_sync.entity.ProjectCharter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@Getter
public class Scope {

            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private long id;

            @ManyToOne
            @JsonIgnore
            @Setter
            @JoinColumn(name = "project_charter_id", nullable = false)
            private ProjectCharter projectCharter;

            @Column(name = "scope_name", nullable = false)
            private String scopeName;

            @Column(name = "scope_content", nullable = false)
            private String scopeContent;

            @Builder
            public Scope(ProjectCharter projectCharter, String scopeName, String scopeContent) {
                    this.projectCharter = projectCharter;
                    this.scopeName = scopeName;
                    this.scopeContent = scopeContent;
            }
}
