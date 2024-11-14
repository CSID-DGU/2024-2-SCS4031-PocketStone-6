import { useParams } from 'react-router-dom';
import { useOneEmployeeInfoQuery } from '../../hooks/useOneEmployeeInfoQuery';
import { useOneEmployeeSpecQuery } from '../../hooks/useOneEmployeeSpecQuery';

export default function EmployeeDetail() {
  const { id } = useParams();
  const oneEmployeeInfoQuery = useOneEmployeeInfoQuery(Number(id));
  const oneEmployeeSpecQuery = useOneEmployeeSpecQuery(Number(id));

  return (
    <div>
      <p>ID: {id} 사원 디테일 정보임</p>
      {JSON.stringify(oneEmployeeInfoQuery.data)}
      {JSON.stringify(oneEmployeeSpecQuery.data)}
    </div>
  );
}
