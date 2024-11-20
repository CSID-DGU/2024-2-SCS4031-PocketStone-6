import { useParams } from 'react-router-dom';
import { MS } from 'styles';

export default function ProjectDetail() {
  const { id } = useParams();

  return (
    <div className={MS.container}>
      <div className={MS.content}>
        <div className={MS.contentTitle}>
          <p>프로젝트 세부 내용</p>
        </div>
        <div className={MS.contentBox}>
          <p>ID: {id} 프로젝트 디테일 요소임</p>
        </div>
      </div>
    </div>
  );
}
