package com.pocketstone.team_sync.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "employee_id"}))
public class ProjectTeammate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(optional=false)
    @JoinColumn(name = "project_id")
    private Project project;//프로젝트정보

    @ManyToOne(optional=false)
    @JoinColumn(name = "employee_id")
    private Employee employee;//사원

    public ProjectTeammate(Project project, Employee employee) {
        this.project = project;
        this.employee = employee;
    }
    
    
}
