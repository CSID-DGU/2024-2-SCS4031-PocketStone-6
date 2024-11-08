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
public class Principle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "principle_name", nullable = false)
    @NotEmpty(message = "원칙을 입력해주세요.")
    private String principleName;

    @ManyToOne
    @JsonIgnore
    @Setter
    @JoinColumn(name = "project_charter_id", nullable = false)
    private ProjectCharter projectCharter;

    @Column(name = "principle_content", nullable = false)
    @NotEmpty(message = "원칙 세부내용을 작성해주세요.")
    private String principleContent;

    @Builder
    public Principle(ProjectCharter projectCharter, String principleName, String principleContent) {
        this.projectCharter = projectCharter;
        this.principleName = principleName;
        this.principleContent = principleContent;
    }
}
