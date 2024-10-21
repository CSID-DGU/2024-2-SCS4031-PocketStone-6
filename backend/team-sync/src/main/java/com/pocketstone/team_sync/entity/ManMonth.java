package com.pocketstone.team_sync.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table
@NoArgsConstructor //(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ManMonth {

    //고유 id
    @Id
    @JsonIgnore
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    //프로젝트 id, 외래키
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "project_id", nullable = false)
    private Project project;

    //포지션
    @Column (name = "position", nullable = false)
    private String position;

    //필요 맨먼스 인력, 소수점 1자리까지
    @Column (name = "man_month", precision = 4, scale = 1, nullable = false)
    private BigDecimal manMonth;


}
