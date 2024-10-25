package com.pocketstone.team_sync.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "project")
@NoArgsConstructor //(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class  Project {

    //프로젝트 고유 id
    @Id
    @JsonIgnore
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    //프로젝트 이름
    @Column (name = "project_name", nullable = false)
    private String projectName;

    //프로젝트 시작일
    @Column (name="start_date", nullable = false)
    private LocalDate startDate;

    //MVP 월 단위까지만 표시
    @DateTimeFormat (pattern = "yyyy-MM")
    @Column (name = "mvp_date", nullable = false)
    private LocalDate mvpDate;



    //@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Timeline> timelines;

    @Builder
    public Project(Long id, String projectName, LocalDate startDate, LocalDate mvpDate) {
        this.id = id;
        this.projectName = projectName;
        this.startDate = startDate;
        this.mvpDate = mvpDate;
    }

}


