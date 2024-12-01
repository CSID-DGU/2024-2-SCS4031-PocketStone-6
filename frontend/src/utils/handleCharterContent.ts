export const handleObjectiveChange = (
  index: number,
  key: keyof Objectives,
  value: string,
  charterContent: CharterContent,
  setCharterContent: React.Dispatch<React.SetStateAction<CharterContent>>
) => {
  if (!charterContent) return;
  const updatedObjectives = [...charterContent.objectives];
  updatedObjectives[index] = {
    ...updatedObjectives[index],
    [key]: value,
  };
  setCharterContent({
    ...charterContent,
    objectives: updatedObjectives,
  });
};

export const handlePrinciplesChange = (
  index: number,
  key: keyof Principles,
  value: string,
  charterContent: CharterContent,
  setCharterContent: React.Dispatch<React.SetStateAction<CharterContent>>
) => {
  if (!charterContent) return;
  const updatedPrinciples = [...charterContent.principles];
  updatedPrinciples[index] = {
    ...updatedPrinciples[index],
    [key]: value,
  };
  setCharterContent({
    ...charterContent,
    principles: updatedPrinciples,
  });
};

export const handleScopesChange = (
  index: number,
  key: keyof Scopes,
  value: string,
  charterContent: CharterContent,
  setCharterContent: React.Dispatch<React.SetStateAction<CharterContent>>
) => {
  if (!charterContent) return;
  const updatedScopes = [...charterContent.scopes];
  updatedScopes[index] = {
    ...updatedScopes[index],
    [key]: value,
  };
  setCharterContent({
    ...charterContent,
    scopes: updatedScopes,
  });
};

export const handleVisionsChange = (
  index: number,
  key: keyof Visions,
  value: string,
  charterContent: CharterContent,
  setCharterContent: React.Dispatch<React.SetStateAction<CharterContent>>
) => {
  if (!charterContent) return;
  const updatedVisions = [...charterContent.visions];
  updatedVisions[index] = {
    ...updatedVisions[index],
    [key]: value,
  };
  setCharterContent({
    ...charterContent,
    visions: updatedVisions,
  });
};

export const handleStakeholdersChange = (
  index: number,
  key: keyof Stakeholders,
  value: string,
  charterContent: CharterContent,
  setCharterContent: React.Dispatch<React.SetStateAction<CharterContent>>
) => {
  if (!charterContent) return;
  const updatedStakeholders = [...charterContent.stakeholders];
  updatedStakeholders[index] = {
    ...updatedStakeholders[index],
    [key]: value,
  };
  setCharterContent({
    ...charterContent,
    stakeholders: updatedStakeholders,
  });
};

export const handleRisksChange = (
  index: number,
  key: keyof Risks,
  value: string,
  charterContent: CharterContent,
  setCharterContent: React.Dispatch<React.SetStateAction<CharterContent>>
) => {
  if (!charterContent) return;
  const updatedRisks = [...charterContent.risks];
  updatedRisks[index] = {
    ...updatedRisks[index],
    [key]: value,
  };
  setCharterContent({
    ...charterContent,
    risks: updatedRisks,
  });
};
