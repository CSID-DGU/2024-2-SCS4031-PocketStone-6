package com.pocketstone.team_sync.dto.projectdto.charterdto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pocketstone.team_sync.entity.charter.Scope;
import com.pocketstone.team_sync.entity.ProjectCharter;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScopeDto {
    private Long id;
    @NotEmpty
    private String scopeName;
    @NotNull
    private String scopeContent;

    public ScopeDto(String scopeName, String scopeContent) {
        this.scopeName = scopeName;
        this.scopeContent = scopeContent;
    }

    @JsonCreator
    public ScopeDto(@JsonProperty("id") Long id,
                    @JsonProperty("scopeName") String scopeName,
                    @JsonProperty("scopeContent") String scopeContent) {
        this.id = id;
        this.scopeName = scopeName;
        this.scopeContent = scopeContent;
    }

    public Scope toScope(ProjectCharter projectCharter, ScopeDto scopeDto) {
        return Scope.builder()
                .projectCharter(projectCharter)
                .scopeName(scopeDto.getScopeName())
                .scopeContent(scopeDto.getScopeContent())
                .build();
    }

    public ScopeDto toScopeDto(Scope scope) {
        return new ScopeDto(scope.getScopeName(), scope.getScopeContent());
    }
}
