import { useQuery } from '@tanstack/react-query';
import { getSprintTotalManmonth } from '../api/evaluations/getSprintTotalManmonth';


export const useSprintTotalManmonthQuery = (projectId: string | number) => {
  const query = useQuery({
    queryKey: ['sprintTotalManmonth', projectId],
    queryFn: () => getSprintTotalManmonth(projectId),
  });

  return query;
};