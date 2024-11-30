import { useParams } from 'react-router-dom';
import { MS } from 'styles';

export default function ProjectCharter() {
  const { id } = useParams();
  return (
    <div className={MS.container}>
      <p>{id} 프로젝트 차터</p>
    </div>
  );
}
