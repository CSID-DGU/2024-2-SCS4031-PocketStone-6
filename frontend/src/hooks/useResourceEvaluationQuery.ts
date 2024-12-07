import { useQuery } from '@tanstack/react-query';
import { getResourceEvaluation } from '../api/evaluations/getResourceEvaluation';

export const useResourceEvaluationQuery = (projectId: string | number) => {
  const query = useQuery({
    queryKey: ['resourceEvaluation', projectId],
    queryFn: () => getResourceEvaluation(projectId),
  });

  return query;
};