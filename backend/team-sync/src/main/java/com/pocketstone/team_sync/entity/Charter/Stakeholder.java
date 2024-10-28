package com.pocketstone.team_sync.entity.Charter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pocketstone.team_sync.entity.ProjectCharter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
public class Stakeholder {

        @Id
        @JsonIgnore
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @ManyToOne
        @JoinColumn(name = "project_charter_id", nullable = false) // Foreign key reference
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
