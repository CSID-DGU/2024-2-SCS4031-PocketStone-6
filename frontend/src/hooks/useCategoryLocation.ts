import { useLocation } from 'react-router-dom';

export const useCategoryLocation = () => {
  const location = useLocation()
  return location.pathname.split('/')[1]
};
