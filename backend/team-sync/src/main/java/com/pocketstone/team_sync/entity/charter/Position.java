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
public class Position {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @ManyToOne
        @JsonIgnore
        @Setter
        @JoinColumn(name = "project_charter_id", nullable = false)
        private ProjectCharter projectCharter;

        @Column(name = "position_name", nullable = false)
        @NotEmpty(message = "포지션을 입력해주세요.")
        private String positionName;

        @Column(name = "position_content", nullable = false)
        @NotEmpty (message = "포지션 역할을 작성해주세요.")
        private String positionContent;

        @Builder
        public Position(ProjectCharter projectCharter, String positionName, String positionContent) {
                this.projectCharter = projectCharter;
                this.positionName = positionName;
                this.positionContent = positionContent;
        }
}
