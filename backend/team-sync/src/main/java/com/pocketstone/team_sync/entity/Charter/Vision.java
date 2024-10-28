package com.pocketstone.team_sync.entity.Charter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pocketstone.team_sync.entity.ProjectCharter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table
@Entity
@Getter
@NoArgsConstructor
public class Vision {

        @Id
        @JsonIgnore
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;


        @ManyToOne
        @JoinColumn(name = "project_charter_id", nullable = false) // Foreign key reference
        private ProjectCharter projectCharter;

        @Column(name = "vision_name", nullable = false)
        private String visionName;

        @Column(name = "vision_content", nullable = false)
        private String visionContent;

        @Builder
        public Vision(ProjectCharter projectCharter, String visionName, String visionContent) {
                this.projectCharter = projectCharter;
                this.visionName = visionName;
                this.visionContent = visionContent;
        }

}
