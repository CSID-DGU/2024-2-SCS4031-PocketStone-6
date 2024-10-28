import { useNavigate } from 'react-router-dom';
import { useAllProjectQuery } from '../../hooks/useAllProjectQuery';
import S from './Project.module.css';

export default function Project() {
  const allProjectQuery = useAllProjectQuery();
  const navigate = useNavigate();

  return (
    <div className={S.container}>
      <h2>프로젝트 목록</h2>
      {allProjectQuery.data?.map((project: Object) => {
        return <p>{JSON.stringify(project)}</p>;
      })}
      <button
        onClick={() => {
          navigate('/project/new');
        }}
      >
        프로젝트 등록
      </button>
    </div>
  );
}
