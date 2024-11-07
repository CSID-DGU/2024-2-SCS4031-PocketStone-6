package com.pocketstone.team_sync.entity.charter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pocketstone.team_sync.entity.ProjectCharter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity
@Getter
@NoArgsConstructor
public class Vision {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;


        @ManyToOne
        @JsonIgnore
        @Setter
        @JoinColumn(name = "project_charter_id", nullable = false)
        private ProjectCharter projectCharter;

        @Column(name = "vision_name", nullable = false)
        @NotEmpty (message = "비전을 입력해주세요.")
        private String visionName;

        @Column(name = "vision_content", nullable = false)
        @NotEmpty (message = "비전 세부내용을 작성해주세요.")
        private String visionContent;

        @Builder
        public Vision(ProjectCharter projectCharter, String visionName, String visionContent) {
                this.projectCharter = projectCharter;
                this.visionName = visionName;
                this.visionContent = visionContent;
        }

}
