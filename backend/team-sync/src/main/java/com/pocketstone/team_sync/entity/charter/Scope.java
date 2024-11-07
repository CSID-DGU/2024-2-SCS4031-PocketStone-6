package com.pocketstone.team_sync.entity.charter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pocketstone.team_sync.entity.ProjectCharter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
            @NotEmpty (message = "스코프를 입력해주세요.")
            private String scopeName;

            @Column(name = "scope_content", nullable = false)
            @NotEmpty (message = "스코프 세부내용을 작성해주세요.")
            private String scopeContent;

            @Builder
            public Scope(ProjectCharter projectCharter, String scopeName, String scopeContent) {
                    this.projectCharter = projectCharter;
                    this.scopeName = scopeName;
                    this.scopeContent = scopeContent;
            }
}
