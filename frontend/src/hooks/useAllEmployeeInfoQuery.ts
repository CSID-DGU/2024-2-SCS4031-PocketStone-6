import { useQuery } from '@tanstack/react-query';
import { getAllEmployeeInfo } from '../api/employee/getAllEmployeeInfo';

export const useAllEmployeeInfoQuery = () => {
  const query = useQuery({
    queryKey: ['useAllEmployeeInfo'],
    queryFn: getAllEmployeeInfo,
  });

  return query;
};
