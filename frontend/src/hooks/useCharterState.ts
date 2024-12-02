import { useEffect, useState } from 'react';
import { checkIsNoData } from 'utils/checkIsNoData';
import { useProjectDetailInfoQuery } from './useProjectDetailInfoQuery';
import { parseCharterData } from 'utils/parseCharterContent';

export const useCharterState = (projectId: number) => {
  const { charterQuery } = useProjectDetailInfoQuery(Number(projectId));
  const [charterContent, setCharterContent] = useState<CharterContent>({
    objectives: [],
    positions: [],
    principles: [],
    scopes: [],
    visions: [],
    stakeholders: [],
    risks: [],
  });

  useEffect(() => {
    const rawData: any = charterQuery?.data;

    setCharterContent(
      checkIsNoData(rawData)
        ? {
            objectives: [],
            positions: [],
            principles: [],
            scopes: [],
            visions: [],
            stakeholders: [],
            risks: [],
          }
        : rawData
    );
  }, [charterQuery?.data]);

  return { charterContent, setCharterContent };
};
