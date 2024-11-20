export const parseDateToString = (date: Date | null): string => {
  if (date === null) return '';

  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();

  return `${year}-${month}-${day}`;
};
