import { useParams } from 'react-router-dom';
import { MS } from 'styles';

export default function ProjectTimelines() {
  const { id } = useParams();

  return (
    <div className={MS.container}>
      <p>{id} 프로젝트 타임라인</p>
    </div>
  );
}
