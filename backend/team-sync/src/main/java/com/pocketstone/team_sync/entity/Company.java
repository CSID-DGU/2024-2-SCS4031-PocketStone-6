package com.pocketstone.team_sync.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String companyName;

    //회사 설명???
    //@Column(nullable=true)
    //private String introduction;

    //사용자(관리자)는 하나의 회사만을 만들 수 있음.
    @OneToOne(fetch=FetchType.LAZY) 
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // 사원과 양방향 관계 설정
    @JsonIgnore
    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();


}
