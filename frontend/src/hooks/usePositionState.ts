import { useEffect, useState } from 'react';
import { checkIsNoData } from 'utils/checkIsNoData';

export const usePositionState = (positionList: Positions[]) => {
  const [position, setPosition] = useState<Positions[]>([
    {
      positionName: 'PM',
      positionContent: '',
      positionCount: 0,
    },
    {
      positionName: 'BE',
      positionContent: '',
      positionCount: 0,
    },
    {
      positionName: 'FE',
      positionContent: '',
      positionCount: 0,
    },
    {
      positionName: 'DE',
      positionContent: '',
      positionCount: 0,
    },
    {
      positionName: 'DA',
      positionContent: '',
      positionCount: 0,
    },
  ]);

  useEffect(() => {
    setPosition(
      checkIsNoData(positionList)
        ? [
            {
              positionName: 'PM',
              positionContent: '',
              positionCount: 0,
            },
            {
              positionName: 'BE',
              positionContent: '',
              positionCount: 0,
            },
            {
              positionName: 'FE',
              positionContent: '',
              positionCount: 0,
            },
            {
              positionName: 'DE',
              positionContent: '',
              positionCount: 0,
            },
            {
              positionName: 'DA',
              positionContent: '',
              positionCount: 0,
            },
          ]
        : positionList
    );
  }, [positionList]);

  return { position, setPosition };
};
