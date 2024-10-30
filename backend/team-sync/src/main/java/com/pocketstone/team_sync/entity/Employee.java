package com.pocketstone.team_sync.entity;

import com.pocketstone.team_sync.entity.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String employeeId;

    @Column(length = 15)
    private String phoneNumber;

    private String position;//직책

    //부서
    private String department;//enum이나 데베로 바꿀지 고민

    //역할
    @Enumerated(EnumType.STRING) // Enum 값을 문자열로 저장
    private Role role; // 프론트/백 등등

     //사원과 회사 관계
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

   

    
    

}
