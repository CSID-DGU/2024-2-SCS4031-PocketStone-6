package com.pocketstone.team_sync.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class PastProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //해당 사원
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    //프로젝트 내용
    private String projectName;
    private String description;
    //private String role; 사용자가 수행한 걸 추가?
    
}
