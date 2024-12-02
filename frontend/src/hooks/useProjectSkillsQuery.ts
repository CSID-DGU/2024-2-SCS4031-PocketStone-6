import { useQuery } from '@tanstack/react-query';
import { getProjectSkills } from 'api/skill/getProjectSkills';

export const useProjectSkillsQuery = () => {
  const query = useQuery({
    queryKey: ['useProjectSkills'],
    queryFn: getProjectSkills,
  });

  return query;
};
