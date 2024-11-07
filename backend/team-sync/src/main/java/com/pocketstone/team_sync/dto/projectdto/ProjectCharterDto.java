package com.pocketstone.team_sync.dto.projectdto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pocketstone.team_sync.dto.projectdto.charterdto.*;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.ProjectCharter;
import com.pocketstone.team_sync.entity.charter.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

    @Data
    @NoArgsConstructor
    public class ProjectCharterDto {
        private Long id;
        @NotEmpty
        private List<Objective> objectives;
        @NotEmpty
        private List<Position> positions;
        @NotEmpty
        private List<Principle> principles;
        @NotEmpty
        private List<Scope> scopes;
        @NotEmpty
        private List<Vision> visions;
        @NotEmpty
        private List<Stakeholder> stakeholders;
        @NotEmpty
        private List<Risk> risks;

        public ProjectCharterDto(List<Objective> objectives,
                                 List<Position> positions,
                                 List<Principle> principles,
                                 List<Scope> scopes,
                                 List<Vision> visions,
                                 List<Stakeholder> stakeholders,
                                 List<Risk> risks) {
            this.objectives = objectives;
            this.positions = positions;
            this.principles = principles;
            this.scopes = scopes;
            this.visions = visions;
            this.stakeholders = stakeholders;
            this.risks = risks;
        }

        @JsonCreator
        public ProjectCharterDto(@JsonProperty("id") Long id,
                                 List<Objective> objectives,
                                 List<Position> positions,
                                 List<Principle> principles,
                                 List<Scope> scopes,
                                 List<Vision> visions,
                                 List<Stakeholder> stakeholders,
                                 List<Risk> risks) {
            this.id = id;
            this.objectives = objectives;
            this.positions = positions;
            this.principles = principles;
            this.scopes = scopes;
            this.visions = visions;
            this.stakeholders = stakeholders;
            this.risks = risks;
        }

        public ProjectCharter toProjectCharter(Project project, ProjectCharterDto projectCharterDto){
            return ProjectCharter.builder()
                    .project(project)
                    .positions(projectCharterDto.getPositions())
                    .objectives(projectCharterDto.getObjectives())
                    .principles(projectCharterDto.getPrinciples())
                    .risks(projectCharterDto.getRisks())
                    .scopes(projectCharterDto.getScopes())
                    .stakeholders(projectCharterDto.getStakeholders())
                    .visions(projectCharterDto.getVisions())

                    .build();
        }
    }

