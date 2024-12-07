import { useQuery } from '@tanstack/react-query';
import { getSprintEvaluation } from '../api/evaluations/getSprintEvaluation';


export const useSprintEvaluationQuery = (projectId: string | number) => {
  const query = useQuery({
    queryKey: ['sprintEvaluation', projectId],
    queryFn: () => getSprintEvaluation(projectId),
  });

  return query;
};