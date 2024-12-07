import { useQuery } from '@tanstack/react-query';
import { getCompletedProjects } from '../api/projects/getCompletedProjects';

export const useCompletedProjectsQuery = () => {
  const query = useQuery({
    queryKey: ['getCompletedProjects'],
    queryFn: getCompletedProjects,
  });

  return query;
};
