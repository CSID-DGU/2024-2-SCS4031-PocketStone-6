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
@Getter
@NoArgsConstructor
public class Stakeholder {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @ManyToOne
        @JsonIgnore
        @Setter
        @JoinColumn(name = "project_charter_id", nullable = false)
        private ProjectCharter projectCharter;

        @Column(name = "stakeholder_name", nullable = false)
        private String stakeholderName;

        @Column(name = "stakeholder_content", nullable = false)
        private String stakeholderContent;

        @Builder
        public Stakeholder(ProjectCharter projectCharter, String stakeholderName, String stakeholderContent) {
                this.projectCharter = projectCharter;
                this.stakeholderName = stakeholderName;
                this.stakeholderContent = stakeholderContent;
        }
}
