package com.pocketstone.team_sync.service;

import com.pocketstone.team_sync.dto.eval.*;
import com.pocketstone.team_sync.entity.ProjectMember;
import com.pocketstone.team_sync.entity.Timeline;
import com.pocketstone.team_sync.entity.User;
import com.pocketstone.team_sync.entity.charter.Objective;
import com.pocketstone.team_sync.entity.evaluation.*;
import com.pocketstone.team_sync.exception.DataNotFoundException;
import com.pocketstone.team_sync.exception.ProjectNotFoundException;
import com.pocketstone.team_sync.repository.ProjectMemberRepository;
import com.pocketstone.team_sync.repository.TimelineRepository;
import com.pocketstone.team_sync.repository.charter.ObjectiveRepository;
import com.pocketstone.team_sync.repository.eval.*;
import com.pocketstone.team_sync.entity.Project;
import com.pocketstone.team_sync.repository.ProjectRepository;
import com.pocketstone.team_sync.utility.ProjectValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectEvalService {

    private final ProjectRepository projectRepository;
    private final ObjectiveAchievementRepository objectiveAchievementRepository;
    private final ResourcesUsageRepository resourcesUsageRepository;
    private final QualityRepository qualityRepository;
    private final SprintAchievementRepository sprintAchievementRepository;
    private final PeerEvalRepository peerEvalRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final TimelineRepository timelineRepository;
    private final ObjectiveRepository objectiveRepository;

    private Project getProject(User user, Long projectId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException(""));
        ProjectValidationUtils.validateProjectOwner(user.getCompany(), project);
        return project;
    }

    @Transactional
    public ObjectiveAchievementDto saveObjectiveAchievement(User user, Long projectId, ObjectiveAchievementDto dto) {
        Project project = getProject(user, projectId);
        ObjectiveAchievement entity = new ObjectiveAchievement();
        Objective objective = objectiveRepository.findById(dto.getObjectiveId()).orElseThrow(
                DataNotFoundException::new);
        entity.setProject(project);
        entity.setObjective(objective);
        entity.setAchieveRate(dto.getAchieveRate());
        return convertToObjectiveAchievementDto(objectiveAchievementRepository.save(entity));
    }

    @Transactional
    public ResourcesUsageDto saveResourcesUsage(User user, Long projectId, ResourcesUsageDto dto) {
        Project project = getProject(user, projectId);
        ResourcesUsage entity = new ResourcesUsage();
        entity.setProject(project);
        entity.setBudget(dto.getBudget());
        entity.setBudgetSpent(dto.getBudgetSpent());
        return convertToResourcesUsageDto(resourcesUsageRepository.save(entity));
    }

    @Transactional
    public QualityDto saveQuality(User user, Long projectId, QualityDto dto) {
        Project project = getProject(user, projectId);
        Quality entity = new Quality();
        entity.setProject(project);
        entity.setTestCoverage(dto.getTestCoverage());
        entity.setProductQuality(dto.getProductQuality());
        entity.setFoundBugs(dto.getFoundBugs());
        entity.setPerformance(dto.getPerformance());
        entity.setReliability(dto.getReliability());
        return convertToQualityDto(qualityRepository.save(entity));
    }

    @Transactional
    public SprintAchievementDto saveSprintAchievement(User user, Long projectId, SprintAchievementDto dto) {
        Project project = getProject(user, projectId);
        Timeline timeline = timelineRepository.findById(dto.getTimelineId()).orElseThrow(
                DataNotFoundException::new);
        SprintAchievement entity = new SprintAchievement();
        entity.setProject(project);
        entity.setTimeline(timeline);
        entity.setBurndownRate(dto.getBurndownRate());
        return convertToSprintAchievementDto(sprintAchievementRepository.save(entity));
    }

    @Transactional
    public PeerEvalDto savePeerEval(User user, Long projectId, PeerEvalDto dto) {
        Project project = getProject(user, projectId);
        ProjectMember member = projectMemberRepository.findById(dto.getProjectMemberId()).orElseThrow(
                DataNotFoundException::new);
        PeerEval entity = new PeerEval();
        entity.setProject(project);
        entity.setProjectMember(member);
        entity.setPerformanceScore(dto.getPerformanceScore());
        entity.setInterpersonalScore(dto.getInterpersonalScore());
        entity.setExpertiseScore(dto.getExpertiseScore());
        entity.setResponsibilityScore(dto.getResponsibilityScore());
        return convertToPeerEvalDto(peerEvalRepository.save(entity));
    }

    public List<ObjectiveAchievementDto> getObjectiveAchievement(User user, Long projectId) {
        Project project = getProject(user, projectId);
        List<ObjectiveAchievement> objectives = objectiveAchievementRepository.findAllByProject(project);
        List<ObjectiveAchievementDto> objectivesDto = new ArrayList<>();
        for (ObjectiveAchievement objective : objectives){
            objectivesDto.add(convertToObjectiveAchievementDto(objective));
        }
        return objectivesDto;

    }

    public ResourcesUsageDto getResourcesUsage(User user, Long projectId) {
        Project project = getProject(user, projectId);
        ResourcesUsage entity = resourcesUsageRepository.findByProject(project)
                .orElseThrow(DataNotFoundException::new);
        return convertToResourcesUsageDto(entity);
    }

    public QualityDto getQuality(User user, Long projectId) {
        Project project = getProject(user, projectId);
        Quality entity = qualityRepository.findByProject(project)
                .orElseThrow(DataNotFoundException::new);
        return convertToQualityDto(entity);
    }

    public List<SprintAchievementDto> getSprintAchievement(User user, Long projectId) {
        Project project = getProject(user, projectId);
        List<SprintAchievement> sprintList = sprintAchievementRepository.findAllByProject(project);
        List<SprintAchievementDto> sprintListDto = new ArrayList<>();
        for(SprintAchievement sprint : sprintList){
            sprintListDto.add(convertToSprintAchievementDto(sprint));
        }
        return sprintListDto;
    }

    public List<PeerEvalDto> getPeerEval(User user, Long projectId) {
        Project project = getProject(user, projectId);
        List<PeerEval> peerEvalList = peerEvalRepository.findAllByProject(project);
        List<PeerEvalDto> peerEvalDtoList = new ArrayList<>();
        for  (PeerEval peerEval : peerEvalList) {
            peerEvalDtoList.add(convertToPeerEvalDto(peerEval));
        }
        return peerEvalDtoList;
    }

    @Transactional
    public ObjectiveAchievementDto updateObjectiveAchievement(User user, Long projectId, ObjectiveAchievementDto dto) {
        Project project = getProject(user, projectId);
        Objective objective = objectiveRepository.findById(dto.getObjectiveId()).orElseThrow(
                DataNotFoundException::new);
        ObjectiveAchievement entity = objectiveAchievementRepository.findByProjectAndObjective(project, objective)
                .orElseThrow(DataNotFoundException::new);
        entity.setAchieveRate(dto.getAchieveRate());
        return convertToObjectiveAchievementDto(objectiveAchievementRepository.save(entity));
    }

    @Transactional
    public ResourcesUsageDto updateResourcesUsage(User user, Long projectId, ResourcesUsageDto dto) {
        Project project = getProject(user, projectId);
        ResourcesUsage entity = resourcesUsageRepository.findByProject(project)
                .orElseThrow(DataNotFoundException::new);
        entity.setBudget(dto.getBudget());
        entity.setBudgetSpent(dto.getBudgetSpent());
        return convertToResourcesUsageDto(resourcesUsageRepository.save(entity));
    }

    @Transactional
    public QualityDto updateQuality(User user, Long projectId, QualityDto dto) {
        Project project = getProject(user, projectId);
        Quality entity = qualityRepository.findByProject(project)
                .orElseThrow(DataNotFoundException::new);
        entity.setTestCoverage(dto.getTestCoverage());
        entity.setProductQuality(dto.getProductQuality());
        entity.setFoundBugs(dto.getFoundBugs());
        entity.setPerformance(dto.getPerformance());
        entity.setReliability(dto.getReliability());
        return convertToQualityDto(qualityRepository.save(entity));
    }

    @Transactional
    public SprintAchievementDto updateSprintAchievement(User user, Long projectId, SprintAchievementDto dto) {
        Project project = getProject(user, projectId);
        SprintAchievement entity = sprintAchievementRepository.findByTimelineId(dto.getTimelineId())
                .orElseThrow(DataNotFoundException::new);
        entity.setBurndownRate(dto.getBurndownRate());
        return convertToSprintAchievementDto(sprintAchievementRepository.save(entity));
    }

    @Transactional
    public PeerEvalDto updatePeerEval(User user, Long projectId, PeerEvalDto dto) {
        Project project = getProject(user, projectId);
        ProjectMember member = projectMemberRepository.findById(dto.getProjectMemberId()).orElseThrow(
                DataNotFoundException::new);
        PeerEval entity = peerEvalRepository.findByProjectAndProjectMember(project, member)
                .orElseThrow(DataNotFoundException::new);
        entity.setPerformanceScore(dto.getPerformanceScore());
        entity.setInterpersonalScore(dto.getInterpersonalScore());
        entity.setExpertiseScore(dto.getExpertiseScore());
        entity.setResponsibilityScore(dto.getResponsibilityScore());
        return convertToPeerEvalDto(peerEvalRepository.save(entity));
    }

    @Transactional
    public void deleteObjectiveAchievement(User user, Long projectId) {
        Project project = getProject(user, projectId);
        objectiveAchievementRepository.deleteByProject(project);
    }

    @Transactional
    public void deleteResourcesUsage(User user, Long projectId) {
        Project project = getProject(user, projectId);
        resourcesUsageRepository.deleteByProject(project);
    }

    @Transactional
    public void deleteQuality(User user, Long projectId) {
        Project project = getProject(user, projectId);
        qualityRepository.deleteByProject(project);
    }

    @Transactional
    public void deleteSprintAchievement(User user, Long projectId) {
        Project project = getProject(user, projectId);
        sprintAchievementRepository.deleteByProject(project);
    }

    @Transactional
    public void deletePeerEval(User user, Long projectId) {
        Project project = getProject(user, projectId);
        peerEvalRepository.deleteByProject(project);
    }

    private ObjectiveAchievementDto convertToObjectiveAchievementDto(ObjectiveAchievement entity) {
        ObjectiveAchievementDto dto = new ObjectiveAchievementDto();
        dto.setId(entity.getId());
        dto.setObjectiveId(entity.getObjective().getId());
        dto.setAchieveRate(entity.getAchieveRate());
        dto.setObjectiveName(entity.getObjective().getObjectiveName());
        return dto;
    }

    private ResourcesUsageDto convertToResourcesUsageDto(ResourcesUsage entity) {
        ResourcesUsageDto dto = new ResourcesUsageDto();
        dto.setId(entity.getId());
        dto.setBudget(entity.getBudget());
        dto.setBudgetSpent(entity.getBudgetSpent());
        return dto;
    }

    private QualityDto convertToQualityDto(Quality entity) {
        QualityDto dto = new QualityDto();
        dto.setId(entity.getId());
        dto.setTestCoverage(entity.getTestCoverage());
        dto.setProductQuality(entity.getProductQuality());
        dto.setFoundBugs(entity.getFoundBugs());
        dto.setPerformance(entity.getPerformance());
        dto.setReliability(entity.getReliability());
        return dto;
    }

    private SprintAchievementDto convertToSprintAchievementDto(SprintAchievement entity) {
        SprintAchievementDto dto = new SprintAchievementDto();
        dto.setId(entity.getId());
        dto.setTimelineId(entity.getTimeline().getId());
        dto.setBurndownRate(entity.getBurndownRate());
        return dto;
    }

    private PeerEvalDto convertToPeerEvalDto(PeerEval entity) {
        PeerEvalDto dto = new PeerEvalDto();
        dto.setId(entity.getId());
        dto.setProjectMemberId(entity.getProjectMember().getId());
        dto.setPerformanceScore(entity.getPerformanceScore());
        dto.setInterpersonalScore(entity.getInterpersonalScore());
        dto.setExpertiseScore(entity.getExpertiseScore());
        dto.setResponsibilityScore(entity.getResponsibilityScore());
        return dto;
    }
}