import { useParams } from 'react-router-dom';

export default function ProjectDetail() {
  const {id} = useParams();

  return (
    <div>
      <p>ID: {id} 프로젝트 디테일 요소임</p>
    </div>
  );
}
