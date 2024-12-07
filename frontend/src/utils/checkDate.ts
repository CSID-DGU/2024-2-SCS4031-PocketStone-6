import { parseStringToDate } from './parseDate';

export const checkDate = (startDateString: string, mvpDateString: string) => {
  const today = new Date();
  const startDate = parseStringToDate(startDateString);
  const mvpDate = parseStringToDate(mvpDateString);

  if (today < startDate) return 'pending';
  if (today > mvpDate) return 'ended';
  return 'running';
};
