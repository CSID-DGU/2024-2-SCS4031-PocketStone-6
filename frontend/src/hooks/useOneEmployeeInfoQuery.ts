import { useQuery } from '@tanstack/react-query';
import { getEmployeeBasicInfo } from '../api/employee/getEmployeeBasicInfo';

export const useOneEmployeeInfoQuery = (employeeId: number) => {
  const query = useQuery({
    queryKey: ['useOneEmployeeInfo', employeeId],
    queryFn: () => getEmployeeBasicInfo(employeeId),
  });

  return query;
};
