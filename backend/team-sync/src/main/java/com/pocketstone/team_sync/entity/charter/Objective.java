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
public class Objective {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @ManyToOne
        @JsonIgnore
        @Setter
        @JoinColumn(name = "project_charter_id", nullable = false) // Foreign key reference
        private ProjectCharter projectCharter;

        @Column(name = "objective_name", nullable = false)
        @NotEmpty(message = "목적을 입력해주세요.")
        private String objectiveName;

        @Column(name = "objective_content", nullable = false)
        @NotEmpty(message = "목적 세부내용을 작성해주세요.")
        private String objectiveContent;

        @Builder
        public Objective(ProjectCharter projectCharter, String objectiveName, String objectiveContent) {
                this.projectCharter = projectCharter;
                this.objectiveName = objectiveName;
                this.objectiveContent = objectiveContent;
        }





}


