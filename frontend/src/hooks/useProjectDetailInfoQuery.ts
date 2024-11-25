import { useQuery } from '@tanstack/react-query';
import {
  getProjectBasicInfo,
  getProjectCharter,
  getProjectTimelines,
} from 'api/projects/getProjectDetailInfo';

export const useProjectDetailInfoQuery = (projectId: number) => {
  const basicInfoQuery = useQuery({
    queryKey: ['useProjectBasicInfoQuery', projectId],
    queryFn: () => getProjectBasicInfo(projectId),
  });

  const charterQuery = useQuery({
    queryKey: ['useProjectCharterQuery', projectId],
    queryFn: () => getProjectCharter(projectId),
  });

  const timelinesQuery = useQuery({
    queryKey: ['useProjectTimelinesQuery', projectId],
    queryFn: () => getProjectTimelines(projectId),
  });

  return { basicInfoQuery, charterQuery, timelinesQuery };
};
