import { useParams } from 'react-router-dom';

export default function EmployeeDetail() {
  const {id} = useParams();

  return (
    <div>
      <p>ID: {id} 사원 디테일 정보임</p>
    </div>
  );
}
