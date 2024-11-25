import { useQuery } from '@tanstack/react-query';
import { getProjectMember } from 'api/projects/getProjectMember';

export const useProjectMemberQuery = (projectId: number) => {
  const query = useQuery({
    queryKey: ['useProjectMember', projectId],
    queryFn: () => getProjectMember(projectId),
  });

  return query;
};
