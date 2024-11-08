package com.pocketstone.team_sync.entity.charter;

import com.pocketstone.team_sync.entity.ProjectCharter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
@Getter
public class CharterPdf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @OneToOne
    @JoinColumn(name = "project_charter_id", nullable = false)
    private ProjectCharter projectCharter;

    @Column(name = "created_date")
    private String createdDate;


    @Builder
    public CharterPdf(String fileName, ProjectCharter projectCharter, String createdDate) {
        this.fileName = fileName;
        this.projectCharter = projectCharter;
        this.createdDate = createdDate;
    }


}
