export const parseCharterContent = (charterContent: CharterContent): CharterContent => {
  const result = { ...charterContent };
  result.positions = result.positions.filter((pos) => pos.positionCount !== 0);

  return result;
};
