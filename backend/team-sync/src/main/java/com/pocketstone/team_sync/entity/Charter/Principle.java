package com.pocketstone.team_sync.entity.Charter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pocketstone.team_sync.entity.ProjectCharter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
@Getter
public class Principle {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "principle_name", nullable = false)
    private String principleName;

    @ManyToOne
    @JoinColumn(name = "project_charter_id", nullable = false) // Foreign key reference
    private ProjectCharter projectCharter;

    @Column(name = "principle_content", nullable = false)
    private String principleContent;

    @Builder
    public Principle(ProjectCharter projectCharter, String principleName, String principleContent) {
        this.projectCharter = projectCharter;
        this.principleName = principleName;
        this.principleContent = principleContent;
    }
}
