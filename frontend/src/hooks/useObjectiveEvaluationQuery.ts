import { useQuery } from '@tanstack/react-query';
import { getObjectiveEvaluation } from '../api/evaluations/getObjectiveEvaluation';

export const useObjectiveEvaluationQuery = (projectId: string | number) => {
  const query = useQuery({
    queryKey: ['objectiveEvaluation', projectId],
    queryFn: () => getObjectiveEvaluation(projectId),
  });

  return query;
};