export const isMonday = (date: Date, endDate: Date | null) => {
  const day = date.getDay();
  const today = new Date();

  if (endDate !== null) return day === 1 && today <= date && endDate > date;
  return day === 1 && today <= date;
};

export const isSunday = (date: Date, startDate: Date | null) => {
  if (startDate === null) return false;
  const day = date.getDay();
  return day === 0 && startDate < date;
};

export const isCanPickMonday = (date: Date, sprintEndDate: Date) => {
  const day = date.getDay();
  const today = new Date();

  return day === 1 && today <= date && sprintEndDate > date;
};

export const isCanPickSunday = (date: Date, sprintStartDate: Date) => {
  const day = date.getDay();

  return day === 0 && sprintStartDate < date;
};
