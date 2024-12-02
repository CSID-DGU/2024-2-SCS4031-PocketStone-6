package com.pocketstone.team_sync.service;

import com.pocketstone.team_sync.entity.charter.*;
import com.pocketstone.team_sync.repository.charter.*;
import org.springframework.stereotype.Service;

import com.pocketstone.team_sync.dto.projectdto.ProjectCharterDto;
import com.pocketstone.team_sync.entity.Company;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.ProjectCharter;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.exception.CharterAlreadyExistsException;
import com.pocketstone.team_sync.exception.CharterNotFoundException;
import com.pocketstone.team_sync.exception.ProjectNotFoundException;
import com.pocketstone.team_sync.repository.CompanyRepository;
import com.pocketstone.team_sync.repository.ProjectCharterRepository;
import com.pocketstone.team_sync.repository.ProjectRepository;
import com.pocketstone.team_sync.utility.ProjectValidationUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Transactional
public class ProjectCharterService {

    private final ProjectCharterRepository projectCharterRepository;
    private final ProjectRepository projectRepository;
    private final ObjectiveRepository objectiveRepository;
    private final ScopeRepository scopeRepository;
    private final PositionRepository positionRepository;
    private final PositionSkillRepository positionSkillRepository;
    private final VisionRepository visionRepository;
    private final StakeholderRepository stakeholderRepository;
    private final PrincipleRepository principleRepository;
    private final RiskRepository riskRepository;
    private final CompanyRepository companyRepository;

    //프로젝트 차터 생성
    public ProjectCharterDto saveProjectCharter(User user, Long projectId, ProjectCharterDto projectCharterDto) {
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(" "));

        ProjectValidationUtils.validateProjectOwner(company, project);
        if (projectCharterRepository.findByProjectId(projectId).isPresent())throw new CharterAlreadyExistsException();


        projectCharterRepository.save(projectCharterDto.toProjectCharter(project, projectCharterDto));

        return projectCharterDto;

    }

    //프로젝트 아이디로 프로젝트 차터 조회
    public ProjectCharterDto findByProjectId(User user, Long projectId) {
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(" "));
        ProjectCharter projectCharter = projectCharterRepository.findByProjectId(projectId)
                .orElseThrow(() -> new CharterNotFoundException());
        ProjectValidationUtils.validateCharterOwner(company, projectCharter);


        return new ProjectCharterDto(
                projectCharter.getId(),
                projectCharter.getObjectives(),
                projectCharter.getPositions(),
                projectCharter.getPrinciples(),
                projectCharter.getScopes(),
                projectCharter.getVisions(),
                projectCharter.getStakeholders(),
                projectCharter.getRisks());
    }

    //프로젝트 차터 수정
    public ProjectCharterDto updateProjectCharterByProjectId(User user, Long projectId, ProjectCharterDto projectCharterDto) {
        Company company  = companyRepository.findByUserId(user.getId()).orElse(null);
        ProjectCharter projectCharter = projectCharterRepository.findByProjectId(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(" "));
        ProjectValidationUtils.validateCharterOwner(company, projectCharter);

        ProjectCharter updatedProjectCharter = projectCharterDto.toProjectCharter(projectCharter.getProject(), projectCharterDto);
        updateObjectives(projectId, updatedProjectCharter);
        updatePositions(projectId, updatedProjectCharter);
        updatePrinciples(projectId, updatedProjectCharter);
        updateScopes(projectId, updatedProjectCharter);
        updateVisions(projectId, updatedProjectCharter);
        updateStakeholders(projectId, updatedProjectCharter);
        updateRisks(projectId, updatedProjectCharter);

        return projectCharterDto;
    }


    private void updateObjectives(Long projectId, ProjectCharter projectCharter) {
        for (Objective objective : projectCharter.getObjectives()) {
            if (objective.getId() == 0){
                ProjectCharter persistentCharter = projectCharterRepository.findByProjectId(projectId)
                        .orElseThrow(() -> new ProjectNotFoundException(" "));
                objective.setProjectCharter(persistentCharter);
                objectiveRepository.save(objective);
            }
            else{
                objectiveRepository.updateObjectiveByProjectId(projectId, objective.getId(), objective.getObjectiveName(), objective.getObjectiveContent());
            }

        }
    }

    private void updatePositions(Long projectId, ProjectCharter projectCharter) {
        for (Position position : projectCharter.getPositions()) {


            if(position.getId() == 0) {
                ProjectCharter persistentCharter = projectCharterRepository.findByProjectId(projectId)
                        .orElseThrow(() -> new ProjectNotFoundException(" "));
                position.setProjectCharter(persistentCharter);

                if (position.getPositionContent() == null) {
                    position.setPositionContent(new HashSet<>());
                }
                for (PositionSkill skill : position.getPositionContent()) {
                    skill.setPosition(position);
                }
                positionRepository.save(position);
            }
            else {
                Position existingPosition = positionRepository.findById(position.getId())
                        .orElseThrow(() -> new RuntimeException("Position not found"));


                existingPosition.getPositionContent().clear();
                positionRepository.save(existingPosition);

                Set<PositionSkill> newSkills = new HashSet<>();
                for (PositionSkill newSkill : position.getPositionContent()) {
                    PositionSkill skillToAdd = new PositionSkill(newSkill.getSkill());
                    skillToAdd.setPosition(existingPosition);
                    newSkills.add(skillToAdd);
                }

                existingPosition.setPositionContent(newSkills);
                existingPosition.setPositionName(position.getPositionName().getLabel());
                existingPosition.setPositionCount(position.getPositionCount());

                positionRepository.save(existingPosition);
            }
        }
    }

    private void updatePrinciples(Long projectId, ProjectCharter projectCharter) {
        for (Principle principle : projectCharter.getPrinciples()) {
            if (principle.getId() == 0) {
                ProjectCharter persistentCharter = projectCharterRepository.findByProjectId(projectId)
                        .orElseThrow(() -> new ProjectNotFoundException(" "));
                principle.setProjectCharter(persistentCharter);
                principleRepository.save(principle);
            } else {
                principleRepository.updatePrincipleByProjectId(projectId, principle.getId(), principle.getPrincipleName(), principle.getPrincipleContent());
            }
        }
    }

    private void updateScopes(Long projectId, ProjectCharter projectCharter) {
        for (Scope scope : projectCharter.getScopes()) {
            if (scope.getId() == 0) {
                ProjectCharter persistentCharter = projectCharterRepository.findByProjectId(projectId)
                        .orElseThrow(() -> new ProjectNotFoundException(" "));
                scope.setProjectCharter(persistentCharter);
                scopeRepository.save(scope);
            } else {
                scopeRepository.updateScopeByProjectId(projectId, scope.getId(), scope.getScopeName(), scope.getScopeContent());
            }
        }
    }

    private void updateVisions(Long projectId, ProjectCharter projectCharter) {
        for (Vision vision : projectCharter.getVisions()) {
            if (vision.getId() == 0) {
                ProjectCharter persistentCharter = projectCharterRepository.findByProjectId(projectId)
                        .orElseThrow(() -> new ProjectNotFoundException(" "));
                vision.setProjectCharter(persistentCharter);
                visionRepository.save(vision);
            } else {
                visionRepository.updateVisionByProjectId(projectId, vision.getId(), vision.getVisionName(), vision.getVisionContent());
            }
        }
    }

    private void updateStakeholders(Long projectId, ProjectCharter projectCharter) {
        for (Stakeholder stakeholder : projectCharter.getStakeholders()) {
            if (stakeholder.getId() == 0) {
                ProjectCharter persistentCharter = projectCharterRepository.findByProjectId(projectId)
                        .orElseThrow(() -> new ProjectNotFoundException(" "));
                stakeholder.setProjectCharter(persistentCharter);
                stakeholderRepository.save(stakeholder);
            } else {
                stakeholderRepository.updateStakeholderByProjectId(projectId, stakeholder.getId(), stakeholder.getStakeholderName(), stakeholder.getStakeholderContent());
            }
        }
    }

    private void updateRisks(Long projectId, ProjectCharter projectCharter) {
        for (Risk risk : projectCharter.getRisks()) {
            if (risk.getId() == 0) {
                ProjectCharter persistentCharter = projectCharterRepository.findByProjectId(projectId)
                        .orElseThrow(() -> new ProjectNotFoundException(" "));
                risk.setProjectCharter(persistentCharter);
                riskRepository.save(risk);
            } else {
                riskRepository.updateRiskByProjectId(projectId, risk.getId(), risk.getRiskName(), risk.getRiskContent());
            }
        }
    }


    public void deleteProjectCharter(User user, Long projectId) {
        ProjectCharter projectCharter = projectCharterRepository.findByProjectId(projectId)
                .orElseThrow(CharterNotFoundException::new);
        ProjectValidationUtils.validateCharterOwner(user.getCompany(), projectCharter);

        projectCharterRepository.delete(projectCharter);
    }
}


