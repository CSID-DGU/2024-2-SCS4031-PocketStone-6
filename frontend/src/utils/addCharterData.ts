export const addObjectives = (charterContent: CharterContent) => {
  const basicObjectives: Objectives = {
    objectiveName: '새 목표 제목',
    objectiveContent: '새 목표 내용',
  };

  return {
    ...charterContent,
    objectives: charterContent?.objectives
      ? [...charterContent.objectives, basicObjectives]
      : [basicObjectives],
  };
};

export const addPrinciples = (charterContent: CharterContent) => {
  const basicPrinciples: Principles = {
    principleName: '새 원칙 제목',
    principleContent: '새 원칙 내용',
  };

  return {
    ...charterContent,
    principles: charterContent?.principles
      ? [...charterContent.principles, basicPrinciples]
      : [basicPrinciples],
  };
};

export const addScopes = (charterContent: CharterContent) => {
  const basicScopes: Scopes = {
    scopeName: '새 범위 제목',
    scopeContent: '새 범위 내용',
  };

  return {
    ...charterContent,
    scopes: charterContent?.scopes ? [...charterContent.scopes, basicScopes] : [basicScopes],
  };
};

export const addVisions = (charterContent: CharterContent) => {
  const basicVisions: Visions = {
    visionName: '새 비전 제목',
    visionContent: '새 비전 내용',
  };

  return {
    ...charterContent,
    visions: charterContent?.visions ? [...charterContent.visions, basicVisions] : [basicVisions],
  };
};

export const addStakeholders = (charterContent: CharterContent) => {
  const basicStakeholders: Stakeholders = {
    stakeholderName: '새 이해관계자 이름',
    stakeholderContent: '새 이해관계자 내용',
  };

  return {
    ...charterContent,
    stakeholders: charterContent?.stakeholders
      ? [...charterContent.stakeholders, basicStakeholders]
      : [basicStakeholders],
  };
};

export const addRisks = (charterContent: CharterContent) => {
  const basicRisks: Risks = {
    riskName: '새 위험 요소 제목',
    riskContent: '새 위험 요소 내용',
  };

  return {
    ...charterContent,
    risks: charterContent?.risks ? [...charterContent.risks, basicRisks] : [basicRisks],
  };
};
