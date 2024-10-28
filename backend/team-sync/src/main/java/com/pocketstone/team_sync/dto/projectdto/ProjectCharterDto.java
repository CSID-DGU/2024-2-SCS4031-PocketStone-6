package com.pocketstone.team_sync.dto.projectdto;

import com.pocketstone.team_sync.dto.projectdto.charterdto.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

    @Data
    @NoArgsConstructor
    public class ProjectCharterDto {
        private List<ObjectiveDto> objectives;
        private List<PositionDto> positions;
        private List<PrincipleDto> principles;
        private List<ScopeDto> scopes;
        private List<VisionDto> visions;
        private List<StakeholderDto> stakeholders;
        private List<RiskDto> risks;

        public ProjectCharterDto(List<ObjectiveDto> objectives, List<PositionDto> positions, List<PrincipleDto> principles, List<ScopeDto> scopes, List<VisionDto> visions, List<StakeholderDto> stakeholders, List<RiskDto> risks) {
            this.objectives = objectives;
            this.positions = positions;
            this.principles = principles;
            this.scopes = scopes;
            this.visions = visions;
            this.stakeholders = stakeholders;
            this.risks = risks;
        }
    }

