import { useParams } from 'react-router-dom';
import { MS } from 'styles';
import S from './ProjectDetail.module.scss'

export default function ProjectDetail() {
  const { id } = useParams();

  return (
    <div className={MS.container}>
      <div className={S.doubleContentDiv}>
        <div className={MS.content}>
          <div className={MS.contentTitle}>
            <p>프로젝트 정보</p>
          </div>
          <div className={MS.contentBox}>
            <p>ID: {id} 며칠부터 며칠까지, 프로젝트 세부 정보(charter)</p>
          </div>
        </div>
        <div className={MS.content}>
          <div className={MS.contentTitle}>
            <p>타임라인</p>
          </div>
          <div className={MS.contentBox}>
            <p>ID: {id} 타임라인 정보가 들어감</p>
          </div>
        </div>
      </div>
      <div className={MS.content}>
          <div className={MS.contentTitle}>
            <p>프로젝트 인원 수정</p>
          </div>
          <div className={MS.contentBox}>
            <p>ID: {id} 인원 정보가 들어감</p>
          </div>
        </div>
    </div>
  );
}
