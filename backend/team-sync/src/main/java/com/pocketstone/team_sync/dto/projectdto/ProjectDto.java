package com.pocketstone.team_sync.dto.projectdto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pocketstone.team_sync.entity.Project;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;


@Data
public class ProjectDto {
    private Long id;
    @NotEmpty (message = "프로젝트 이름을 입력해주세요.")
    private String projectName;
    private LocalDate startDate;
    @Future (message = "날짜를 다시 확인해주세요")
    private LocalDate mvpDate;

    public ProjectDto(String projectName, LocalDate startDate, LocalDate mvpDate) {
        this.projectName = projectName;
        this.startDate = startDate;
        this.mvpDate = mvpDate;
    }

    @JsonCreator
    public ProjectDto(@JsonProperty("id") Long id,
                       @JsonProperty("projectName") String projectName,
                       @JsonProperty("startDate") LocalDate startDate,
                       @JsonProperty("mvpDate") LocalDate mvpDate) {
        this.id = id;
        this.projectName = projectName;
        this.startDate = startDate;
        this.mvpDate = mvpDate;
    }

    public  static ProjectDto toProjectDto(Project project) {
        return new ProjectDto(
                project.getId(),
                project.getProjectName(),
                project.getStartDate(),
                project.getMvpDate());
    }
}




