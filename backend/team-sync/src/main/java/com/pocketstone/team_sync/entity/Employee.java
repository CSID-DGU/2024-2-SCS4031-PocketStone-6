package com.pocketstone.team_sync.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pocketstone.team_sync.entity.enums.Role;
import com.pocketstone.team_sync.entity.enums.Skill;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta. persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"company_id", "staffId"}),
        @UniqueConstraint(columnNames = {"company_id", "email"})
    }
)
@Entity
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", updatable = false)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    //사원아이디
    @Column(nullable = false)
    private String staffId;

    @Column(length = 15)
    private String phoneNumber;

    private String position;//직책

    //입사일
    private LocalDate hireDate;

    //부서
    private String department;//enum이나 데베로 바꿀지 고민


    //역할
    @Enumerated(EnumType.STRING) // Enum 값을 문자열로 저장
    private Role role; // 프론트/백 등등

    //스킬 목록
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "employee_skill", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "skill")
    @Enumerated(EnumType.STRING)
    private List<Skill> skillSet;
    //역량 점수
    private double skillScore;

    //성향
    @Lob
    @Column
    private String personalType;

    //kpi평가점수
    private double kpiScore;

    //동료 평가 점수
    private double peerEvaluationScore;

    //project-현재 참여중인 -projectTeam으로 중간 매핑
    
    //과거 프로젝트 내역
    // 양방향 관계 설정
    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<PastProject> pastProjects = new ArrayList<>();
    
    
    //사원과 회사 관계
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

   

    
    

}
