export const parseCharterContent = (charterContent: CharterContent): CharterChangedContent => {
  const result: CharterChangedContent = {
    ...charterContent,
    positions: charterContent.positions
      .filter((pos) => pos.positionCount !== 0)
      .map((pos) => ({
        id: pos.id || 0,
        positionName: pos.positionName,
        positionContent: pos.positionContent.split(',').map((skill) => ({ skill: skill.trim() })),
        positionCount: pos.positionCount,
      })),
  };

  return result;
};

export const parseCharterData = (charterData: CharterChangedContent): CharterContent => {
  const result: CharterContent = {
    ...charterData,
    positions: charterData.positions.map((pos) => {
      return {
        id: pos.id,
        positionName: pos.positionName,
        positionContent: pos.positionContent.map((skillObject) => skillObject.skill).join(','),
        positionCount: pos.positionCount,
      };
    }),
  };

  return result;
};
