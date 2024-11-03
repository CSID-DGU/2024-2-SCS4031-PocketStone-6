import { useQuery } from '@tanstack/react-query';
import { getEmployeeSpec } from '../api/employee/getEmployeeSpec';

export const useOneEmployeeSpecQuery = (employeeId: number) => {
  const query = useQuery({
    queryKey: ['useOneEmployeeSpec', employeeId],
    queryFn: () => getEmployeeSpec(employeeId),
  });

  return query;
};
