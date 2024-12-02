package com.pocketstone.team_sync.entity.charter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pocketstone.team_sync.entity.ProjectCharter;
import com.pocketstone.team_sync.entity.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Table
@Entity
@Getter
@NoArgsConstructor
public class Position {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @ManyToOne
        @JsonIgnore
        @Setter
        @JoinColumn(name = "project_charter_id", nullable = false)
        private ProjectCharter projectCharter;

        @Enumerated(EnumType.STRING)
        @Column(name = "position_name", nullable = false)
        @NotNull(message = "포지션을 선택해주세요.")
        private Role positionName;

        @OneToMany(mappedBy = "position", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonManagedReference
        private Set<PositionSkill> positionContent = new HashSet<>();

        @Column(name = "position_count", nullable = false)
        @Setter
        private Integer positionCount;

        public void setPositionName(String label){
                this.positionName = Role.fromLabel(label);
        }

        @JsonProperty("positionName")
        public String getPositionLabel() {
                return positionName != null ? positionName.getLabel() : null;
        }
        public Position(ProjectCharter projectCharter, String positionName, Set<PositionSkill> positionContent, Integer positionCount) {
                this.projectCharter = projectCharter;
                this.positionName = Role.fromLabel(positionName);
                this.positionCount = positionCount;
                setPositionContent(positionContent);
        }

        public void setPositionContent(Set<PositionSkill> positionContent) {
                this.positionContent.clear();
                if (positionContent != null) {
                        positionContent.forEach(skill -> skill.setPosition(this));
                        this.positionContent.addAll(positionContent);
                }
        }
}
