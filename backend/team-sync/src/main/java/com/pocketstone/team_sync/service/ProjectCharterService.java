package com.pocketstone.team_sync.service;

import com.pocketstone.team_sync.dto.projectdto.ProjectCharterDto;
import com.pocketstone.team_sync.dto.projectdto.charterdto.*;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.entity.ProjectCharter;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.repository.ProjectCharterRepository;
import com.pocketstone.team_sync.repository.ProjectRepository;
import com.pocketstone.team_sync.repository.charter.*;
import com.pocketstone.team_sync.utility.ProjectValidationUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectCharterService {

    @Autowired
    private ProjectCharterRepository projectCharterRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private final ObjectiveRepository objectiveRepository;
    private final ScopeRepository scopeRepository;
    private final PositionRepository positionRepository;
    private final VisionRepository visionRepository;
    private final StakeholderRepository stakeholderRepository;
    private final PrincipleRepository principleRepository;
    private final RiskRepository riskRepository;


    //프로젝트 차터 생성
    public ProjectCharterDto saveProjectCharter(User user, Long projectId, ProjectCharterDto projectCharterDto) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 프로젝트 찾을 수 없음"));


        ProjectValidationUtils.validateProjectOwner(user, project);


        ProjectCharter projectCharter =
                projectCharterRepository.save(ProjectCharter.builder().project(project).build());

            for (ObjectiveDto objectiveDto : projectCharterDto.getObjectives()) {
                objectiveRepository.save(objectiveDto.toObjective(projectCharter, objectiveDto));
            }
            for (ScopeDto scopeDto : projectCharterDto.getScopes()) {
                scopeRepository.save(scopeDto.toScope(projectCharter, scopeDto));
            }
            for (PositionDto positionDto : projectCharterDto.getPositions()) {
                positionRepository.save(positionDto.toPosition(projectCharter, positionDto));
            }
            for (VisionDto visionDto : projectCharterDto.getVisions()) {
                visionRepository.save(visionDto.toVision(projectCharter, visionDto));
            }
            for (StakeholderDto stakeholderDto : projectCharterDto.getStakeholders()) {
                stakeholderRepository.save(stakeholderDto.toStakeholder(projectCharter, stakeholderDto));
            }
            for (PrincipleDto principleDto : projectCharterDto.getPrinciples()) {
                principleRepository.save(principleDto.toPrinciple(projectCharter, principleDto));
            }
            for (RiskDto riskDto : projectCharterDto.getRisks()) {
                riskRepository.save(riskDto.toRisk(projectCharter, riskDto));
            }
        return projectCharterDto;

    }
    //프로젝트 아이디로 프로젝트 차터 조회
    public ProjectCharterDto findByProjectId(User user, Long projectId) {
        ProjectCharter projectCharter = projectCharterRepository.findByProjectId(projectId)
                .orElseThrow(() -> new RuntimeException("해당 프로젝트 찾을 수 없음"));
        ProjectValidationUtils.validateCharterOwner(user, projectCharter);

        List<ObjectiveDto> objectives = projectCharter.getObjectives().stream()
                .map(objective -> new ObjectiveDto(objective.getId(),objective.getObjectiveName(), objective.getObjectiveContent()))
                .collect(Collectors.toList());

        List<PositionDto> positions = projectCharter.getPositions().stream()
                .map(position -> new PositionDto(position.getId(), position.getPositionName(), position.getPositionContent()))
                .collect(Collectors.toList());

        List<PrincipleDto> principles = projectCharter.getPrinciples().stream()
                .map(principle -> new PrincipleDto(principle.getId(), principle.getPrincipleName(), principle.getPrincipleContent()))
                .collect(Collectors.toList());

        List<ScopeDto> scopes = projectCharter.getScopes().stream()
                .map(scope -> new ScopeDto(scope.getId(), scope.getScopeName(), scope.getScopeContent()))
                .collect(Collectors.toList());

        List<VisionDto> visions = projectCharter.getVisions().stream()
                .map(vision -> new VisionDto(vision.getId(), vision.getVisionName(), vision.getVisionContent()))
                .collect(Collectors.toList());

        List<StakeholderDto> stakeholders = projectCharter.getStakeholders().stream()
                .map(stakeholder -> new StakeholderDto(stakeholder.getId(), stakeholder.getStakeholderName(), stakeholder.getStakeholderContent()))
                .collect(Collectors.toList());

        List<RiskDto> risks = projectCharter.getRisks().stream()
                .map(risk -> new RiskDto(risk.getId(), risk.getRiskName(), risk.getRiskContent()))
                .collect(Collectors.toList());

        return new ProjectCharterDto(objectives,
                positions,
                principles,
                scopes,
                visions,
                stakeholders,
                risks);

    }

    //프로젝트 차터 수정
    public ProjectCharterDto updateProjectCharterByProjectId(User user, Long projectId, ProjectCharterDto projectCharterDto) {
        ProjectCharter projectCharter = projectCharterRepository.findByProjectId(projectId)
                .orElseThrow(() -> new RuntimeException("프로젝트 찾을 수 없음"));
        ProjectValidationUtils.validateCharterOwner(user, projectCharter);

        for (ObjectiveDto objectiveDto : projectCharterDto.getObjectives()) {
            objectiveRepository.updateObjectiveByProjectId(
                    projectId,
                    objectiveDto.getId(),
                    objectiveDto.getObjectiveName(),
                    objectiveDto.getObjectiveContent());
        }

        for (ScopeDto scopeDto : projectCharterDto.getScopes()) {
            scopeRepository.updateScopeByProjectId(
                    projectId,
                    scopeDto.getId(),
                    scopeDto.getScopeName(),
                    scopeDto.getScopeContent());
        }

        for (PositionDto positionDto : projectCharterDto.getPositions()) {
            positionRepository.updatePositionByProjectId(
                    projectId,
                    positionDto.getId(),
                    positionDto.getPositionName(),
                    positionDto.getPositionContent());
        }

        for (VisionDto visionDto : projectCharterDto.getVisions()) {
            visionRepository.updateVisionByProjectId(
                    projectId,
                    visionDto.getId(),
                    visionDto.getVisionName(),
                    visionDto.getVisionContent());
        }

        for (StakeholderDto stakeholderDto : projectCharterDto.getStakeholders()) {
            stakeholderRepository.updateStakeholderByProjectId(
                    projectId,
                    stakeholderDto.getId(),
                    stakeholderDto.getStakeholderName(),
                    stakeholderDto.getStakeholderContent());
        }

        for  (PrincipleDto principleDto : projectCharterDto.getPrinciples()) {
            principleRepository.updatePrincipleByProjectId(
                    projectId,
                    principleDto.getId(),
                    principleDto.getPrincipleName(),
                    principleDto.getPrincipleContent());
        }

        for (RiskDto riskDto : projectCharterDto.getRisks()) {
            riskRepository.updateRiskByProjectId(
                    projectId,
                    riskDto.getId(),
                    riskDto.getRiskName(),
                    riskDto.getRiskContent());
        }

        return projectCharterDto;
    }
}
