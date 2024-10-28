package com.pocketstone.team_sync.entity.Charter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pocketstone.team_sync.entity.ProjectCharter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table
@NoArgsConstructor
@Entity
@Getter
public class Risk {

        @Id
        @JsonIgnore
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @ManyToOne
        @JoinColumn(name = "project_charter_id", nullable = false) // Foreign key reference
        private ProjectCharter projectCharter;

        @Column(name = "risk_name", nullable = false)
        private String riskName;

        @Column(name = "risk_content", nullable = false)
        private String riskContent;

        @Builder
        public Risk(ProjectCharter projectCharter, String riskName, String riskContent) {
                this.projectCharter = projectCharter;
                this.riskName = riskName;
                this.riskContent = riskContent;
        }

}
