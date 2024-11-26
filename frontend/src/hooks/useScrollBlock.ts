import { useEffect } from 'react';

export const useScrollBlock = (condition: boolean) => {
  useEffect(() => {
    if (condition) {
      document.body.style.overflow = 'hidden';
    } else {
      document.body.style.overflow = '';
    }

    // Clean-up 함수로 스타일 원상복구
    return () => {
      document.body.style.overflow = '';
    };
  }, [condition]);
};
