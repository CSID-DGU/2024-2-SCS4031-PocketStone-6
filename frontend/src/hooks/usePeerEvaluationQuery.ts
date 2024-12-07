import { useQuery } from '@tanstack/react-query';
import { getPeerEvaluation } from '../api/evaluations/getPeerEvaluation';

export const usePeerEvaluationQuery = (projectId: string | number) => {
  const query = useQuery({
    queryKey: ['peerEvaluation', projectId],
    queryFn: () => getPeerEvaluation(projectId),
  });

  return query;
};