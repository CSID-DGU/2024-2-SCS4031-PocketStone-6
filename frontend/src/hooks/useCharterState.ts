import { useEffect, useState } from 'react';
import { checkIsNoData } from 'utils/checkIsNoData';
import { useProjectDetailInfoQuery } from './useProjectDetailInfoQuery';

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
    setCharterContent(
      checkIsNoData(charterQuery?.data)
        ? {
            objectives: [],
            positions: [],
            principles: [],
            scopes: [],
            visions: [],
            stakeholders: [],
            risks: [],
          }
        : charterQuery?.data
    );
  }, [charterQuery?.data]);

  return { charterContent, setCharterContent };
};
