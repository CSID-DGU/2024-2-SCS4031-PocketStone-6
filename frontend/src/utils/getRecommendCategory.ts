export const getRecommendCategory = (category: number) => {
  if (category === 0) return 'basic';
  if (category === 1) return 'skill';
  if (category === 2) return 'domain';
  if (category === 3) return 'personality';
};
