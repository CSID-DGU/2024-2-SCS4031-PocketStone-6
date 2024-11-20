import { useParams } from 'react-router-dom';
import { useOneEmployeeInfoQuery } from '../../hooks/useOneEmployeeInfoQuery';
import { useOneEmployeeSpecQuery } from '../../hooks/useOneEmployeeSpecQuery';
import MS from 'styles/Main.module.scss'

export default function EmployeeDetail() {
  const { id } = useParams();
  const oneEmployeeInfoQuery = useOneEmployeeInfoQuery(Number(id));
  const oneEmployeeSpecQuery = useOneEmployeeSpecQuery(Number(id));

  return (
    <div className={MS.container}>
      <p>ID: {id} 사원 디테일 정보임</p>
      {JSON.stringify(oneEmployeeInfoQuery.data)}
      {JSON.stringify(oneEmployeeSpecQuery.data)}
    </div>
  );
}
