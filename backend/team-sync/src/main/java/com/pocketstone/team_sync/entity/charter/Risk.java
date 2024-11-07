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
@NoArgsConstructor
@Entity
@Getter
public class Risk {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @ManyToOne
        @JsonIgnore
        @Setter
        @JoinColumn(name = "project_charter_id", nullable = false)
        private ProjectCharter projectCharter;

        @Column(name = "risk_name", nullable = false)
        @NotEmpty (message = "리스크를 입력해주세요.")
        private String riskName;

        @Column(name = "risk_content", nullable = false)
        @NotEmpty(message = "리스크 세부내용을 작성해주세요.")
        private String riskContent;

        @Builder
        public Risk(ProjectCharter projectCharter, String riskName, String riskContent) {
                this.projectCharter = projectCharter;
                this.riskName = riskName;
                this.riskContent = riskContent;
        }

}
