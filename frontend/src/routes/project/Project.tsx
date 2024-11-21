import { useNavigate } from 'react-router-dom';
import { useAllProjectQuery } from '../../hooks/useAllProjectQuery';
import S from './Project.module.scss';
import ProjectBlockStyle from './ProjectBlock.module.scss';
import { UseQueryResult } from '@tanstack/react-query';
import { BS, MS } from 'styles';

export default function Project() {
  const navigate = useNavigate();

  return (
    <div className={MS.container}>
      <div className={MS.content}>
        <div className={MS.contentTitle}>
          <p>프로젝트 목록</p>
        </div>
        <div className={MS.contentBox}>
          <button
            className={`${BS.YellowBtn} ${S.newProjectBtn}`}
            onClick={() => {
              navigate('/project/new');
            }}>
            ➕ 새로운 프로젝트
          </button>
          <ProjectContent />
        </div>
      </div>
    </div>
  );
}

const ProjectContent = () => {
  const allProjectQuery = useAllProjectQuery();

  return (
    <div className={S.contentTitle}>
      <div className={MS.displayFlex}>
        <div className={`${S.category} ${MS.flexThree}`}>프로젝트명</div>
        <div className={`${S.category} ${MS.flexTwo}`}>시작일</div>
        <div className={`${S.category} ${MS.flexTwo}`}>종료일</div>
        <div className={`${S.category} ${MS.flexOne}`}></div>
      </div>
      <div className={S.contentBox}>
        {allProjectQuery.data === undefined || allProjectQuery.data.length === 0 ? (
          <NoProjectData />
        ) : (
          <ProjectData allProjectQuery={allProjectQuery} />
        )}
      </div>
    </div>
  );
};

const NoProjectData = () => {
  return (
    <div className={S.notice}>
      <p>사원 정보가 없어요.</p>
      <p>사원정보 등록을 통해 정보를 등록해보세요!</p>
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
              className={BS.YellowBtn}
              onClick={async () => {
                alert('즐거운 크리스마스 되세요');
              }}>
              삭제
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
