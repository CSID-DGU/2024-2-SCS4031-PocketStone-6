package com.pocketstone.team_sync.entity.charter;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pocketstone.team_sync.entity.enums.Skill;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table
@Entity
@Getter
@NoArgsConstructor
public class PositionSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "position_id")
    @Setter
    @JsonBackReference
    private Position position;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Skill skill;

    public void setSkill(String skillLabel) {
        this.skill = Skill.fromLabel(skillLabel);
    }

    public String getSkill() {
        return skill.getLabel();
    }

    public PositionSkill(String skillLabel) {
        this.skill = Skill.fromLabel(skillLabel);
    }
}