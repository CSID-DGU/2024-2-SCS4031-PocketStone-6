package com.pocketstone.team_sync.entity;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;


    //@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<Timeline> timelines;

    @Builder
    public Project(Long id, String projectName, LocalDate startDate, LocalDate mvpDate, Company company) {
        this.id = id;
        this.projectName = projectName;
        this.startDate = startDate;
        this.mvpDate = mvpDate;
        this.company = company;
    }

}


