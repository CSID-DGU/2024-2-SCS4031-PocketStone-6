import { useQuery } from '@tanstack/react-query';
import {
  getProjectBasicInfo,
  getProjectCharter,
  getProjectTimelines,
} from 'api/projects/getProjectDetailInfo';
import { parseCharterData } from 'utils/parseCharterContent';

export const useProjectDetailInfoQuery = (projectId: number) => {
  const basicInfoQuery = useQuery({
    queryKey: ['useProjectBasicInfoQuery', projectId],
    queryFn: () => getProjectBasicInfo(projectId),
  });

  const charterQuery = useQuery({
    queryKey: ['useProjectCharterQuery', projectId],
    queryFn: async () => {
      const data = await getProjectCharter(projectId); // 원본 데이터 가져오기
      return parseCharterData(data); // 데이터를 가공하여 반환
    },
  });

  const timelinesQuery = useQuery({
    queryKey: ['useProjectTimelinesQuery', projectId],
    queryFn: () => getProjectTimelines(projectId),
  });

  return { basicInfoQuery, charterQuery, timelinesQuery };
};
