import { useNavigate } from 'react-router-dom';
import { useAllProjectQuery } from '../../hooks/useAllProjectQuery';
import S from './Project.module.scss';
import ProjectBlockStyle from './ProjectBlock.module.scss';
import { UseQueryResult } from '@tanstack/react-query';
import { BS, MS } from 'styles';
import { checkIsNoData } from 'utils/checkIsNoData';
import { FaTrash } from 'react-icons/fa';
import { deleteProject } from 'api/projects/deleteProject';

export default function Project() {
  const navigate = useNavigate();

  return (
    <div className={MS.container}>
      <div className={MS.content}>
        <div className={`${MS.contentTitle} ${MS.displayFlex} ${MS.flexSpace}`}>
          <p>프로젝트 목록</p>
          <button
            className={BS.YellowBtn}
            onClick={() => {
              navigate('/project/new');
            }}>
            ➕ 새로운 프로젝트
          </button>
        </div>
        <div className={MS.contentBox}>
          <ProjectContent />
        </div>
      </div>
    </div>
  );
}

const ProjectContent = () => {
  const allProjectQuery = useAllProjectQuery();

  return (
    <>
      <div className={S.contentTitle}>
        <div className={MS.displayFlex}>
          <div className={`${S.category} ${MS.flexThree}`}>프로젝트명</div>
          <div className={`${S.category} ${MS.flexTwo}`}>시작일</div>
          <div className={`${S.category} ${MS.flexTwo}`}>종료일</div>
          <div className={`${S.category} ${MS.flexOne}`}></div>
        </div>
      </div>

      <div className={S.contentBox}>
        {checkIsNoData(allProjectQuery.data) ? (
          <NoProjectData />
        ) : (
          <ProjectData allProjectQuery={allProjectQuery} />
        )}
      </div>
    </>
  );
};

const NoProjectData = () => {
  return (
    <div className={S.notice}>
      <p>프로젝트가 없어요.</p>
      <p>"새로운 프로젝트" 버튼을 눌러 새로 등록해주세요!</p>
    </div>
  );
};

const ProjectData = ({ allProjectQuery }: { allProjectQuery: UseQueryResult<any> }) => {
  return allProjectQuery.data?.map(
    ({ id, projectName, startDate, mvpDate }: projectInfoType, i: number) => (
      <ProjectBlock
        key={i}
        id={id}
        projectName={projectName}
        startDate={startDate}
        mvpDate={mvpDate}
      />
    )
  );
};

const ProjectBlock = ({ id, projectName, startDate, mvpDate }: projectInfoType) => {
  const navigate = useNavigate();

  return (
    <div className={ProjectBlockStyle.container}>
      <div className={ProjectBlockStyle.card}>
        <div
          className={ProjectBlockStyle.canClickPart}
          onClick={() => {
            navigate(`/project/${id}`);
          }}>
          <div className={`${S.category} ${MS.flexThree}`}>{projectName}</div>
          <div className={`${S.category} ${MS.flexTwo}`}>{startDate}</div>
          <div className={`${S.category} ${MS.flexTwo}`}>{mvpDate}</div>
        </div>
        <div className={ProjectBlockStyle.noClickPart}>
          <div className={`${S.category} ${MS.flexOne}`}>
            <button
              className={`${BS.removeBtn} ${MS.width100}`}
              onClick={() => {
                deleteProject(id, navigate);
              }}>
              <FaTrash />
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
