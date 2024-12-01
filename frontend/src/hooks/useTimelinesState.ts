import { useEffect, useState } from 'react';
import { checkIsNoData } from 'utils/checkIsNoData';
import { useProjectDetailInfoQuery } from './useProjectDetailInfoQuery';

export const useTimelinesState = (projectId: number) => {
  const { timelinesQuery } = useProjectDetailInfoQuery(Number(projectId));
  const [timelinesContent, setTimelinesContent] = useState<TimelineData[]>([]);

  useEffect(() => {
    setTimelinesContent(checkIsNoData(timelinesQuery?.data) ? [] : timelinesQuery?.data);
  }, [timelinesQuery?.data]);

  return { timelinesContent, setTimelinesContent };
};
