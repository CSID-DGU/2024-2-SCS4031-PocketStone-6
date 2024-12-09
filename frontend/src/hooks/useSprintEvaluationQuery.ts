import { useQuery } from '@tanstack/react-query';
import { getSprintEvaluation } from '../api/evaluations/getSprintEvaluation';


export const useSprintEvaluationQuery = (projectId: string | number, memberId: string | number) => {
  const query = useQuery({
    queryKey: ['sprintEvaluation', projectId, memberId],
    queryFn: () => getSprintEvaluation(projectId, memberId),
  });

  return query;
};