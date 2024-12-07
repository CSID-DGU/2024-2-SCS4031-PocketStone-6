import { useQuery } from '@tanstack/react-query';
import { getQualityEvaluation } from '../api/evaluations/getQualityEvaluation';

export const useQualityEvaluationQuery = (projectId: string | number) => {
  const query = useQuery({
    queryKey: ['qualityEvaluation', projectId],
    queryFn: () => getQualityEvaluation(projectId),
  });

  return query;
};