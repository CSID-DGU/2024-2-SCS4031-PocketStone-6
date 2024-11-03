import { useQuery } from '@tanstack/react-query';
import { getAllProject } from '../api/projects/getProject';

export const useAllProjectQuery = () => {
  const query = useQuery({
    queryKey: ['useAllProject'],
    queryFn: getAllProject,
  });

  return query;
};
