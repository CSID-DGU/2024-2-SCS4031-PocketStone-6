import { useNavigate } from 'react-router-dom';
import { useAllProjectQuery } from '../../hooks/useAllProjectQuery';

export default function Project() {
  const allProjectQuery = useAllProjectQuery();
  const navigate = useNavigate();

  return (
    <div>
      <h2>프로젝트 목록</h2>
      {allProjectQuery.data?.map((project: projectInfoType) => (
        <ProjectBlock
          id={project.id}
          projectName={project.projectName}
          startDate={project.startDate}
          mvpDate={project.mvpDate}
        />
      ))}
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

const ProjectBlock = ({
  id,
  projectName,
  startDate,
  mvpDate,
}: projectInfoType) => {
  const navigate = useNavigate();

  return (
    <div
      onClick={() => {
        navigate(`/project/${id}`);
      }}
    >
      {id} {projectName} {startDate} {mvpDate}
    </div>
  );
};
