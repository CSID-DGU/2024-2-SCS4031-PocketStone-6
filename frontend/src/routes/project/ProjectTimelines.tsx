import { useParams } from 'react-router-dom';
import { MS } from 'styles';

export default function ProjectTimelines() {
  const { id } = useParams();

  return (
    <div className={MS.container}>
      <div className={MS.content}>
        <div className={MS.contentTitle}>
          <p>프로젝트 타임라인 수정</p>
        </div>
        <div className={MS.contentBox}>
            <p>{id} 타임라인 정보 및 수정</p>
        </div>
      </div>
    </div>
  );
}
