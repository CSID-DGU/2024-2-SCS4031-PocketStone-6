import { useNavigate, useParams } from 'react-router-dom';
import { useOneEmployeeInfoQuery } from '../../hooks/useOneEmployeeInfoQuery';
import { useOneEmployeeSpecQuery } from '../../hooks/useOneEmployeeSpecQuery';
import { MS } from 'styles';
import S from 'styles/EmployDetail.module.scss';
import { parsePhoneNumber } from 'utils/parsePhoneNumber';
import { FaPhoneAlt, FaSearch, FaQuestionCircle, FaDotCircle } from 'react-icons/fa';
import { IoIosMail } from 'react-icons/io';
import { BiSolidPencil, BiSolidReport } from 'react-icons/bi';
import { MdLocalLibrary, MdOutlineDateRange } from 'react-icons/md';
import { PiProjectorScreenChartFill } from 'react-icons/pi';
import { BsCursorFill } from 'react-icons/bs';
import { checkIsNoData } from 'utils/checkIsNoData';
import { checkDate } from 'utils/checkDate';

export default function EmployeeDetail() {
  const { id } = useParams();

  return (
    <div className={MS.container}>
      {/* <p>ID: {id} 사원 디테일 정보임</p> */}
      <div className={MS.content}>
        <div className={MS.contentTitle}>
          <p>사원 상세 정보</p>
        </div>
        <div className={MS.contentBox}>
          <BasicInfo id={Number(id)} />
          <SpecInfo id={Number(id)} />
        </div>
      </div>
    </div>
  );
}

const BasicInfo = ({ id }: { id: number }) => {
  const oneEmployeeInfoQuery = useOneEmployeeInfoQuery(id);
  return (
    <div className={S.profile}>
      <img src="/images/profile.png" alt="프로필이미지" />
      <div className={S.profileInfo}>
        <div>
          <p className={S.staffId}>{oneEmployeeInfoQuery.data?.staffId}</p>
          <div className={S.namePositionInfo}>
            <p className={S.name}>{oneEmployeeInfoQuery.data?.name}</p>
            <p className={S.position}>{oneEmployeeInfoQuery.data?.position}</p>
            <p className={S.position}>{oneEmployeeInfoQuery.data?.department}</p>
          </div>
        </div>
        <div>
          <p className={S.iconInfo}>
            <FaPhoneAlt />
            {parsePhoneNumber(oneEmployeeInfoQuery.data?.phoneNumber)}
          </p>
          <p className={S.iconInfo}>
            <IoIosMail />
            {oneEmployeeInfoQuery.data?.email}
          </p>
          <p className={S.iconInfo}>
            <MdOutlineDateRange />
            {oneEmployeeInfoQuery.data?.hireDate}
          </p>
        </div>
      </div>
    </div>
  );
};

const SpecInfo = ({ id }: { id: number }) => {
  const oneEmployeeSpecQuery = useOneEmployeeSpecQuery(id);
  return (
    <div className={S.spec}>
      <div className={S.rowInfo}>
        <p className={S.rowTitle}>
          <FaQuestionCircle />
          역할
        </p>
        <p className={S.keyword}>{oneEmployeeSpecQuery.data?.role}</p>
      </div>

      <div className={S.rowInfo}>
        <p className={S.rowTitle}>
          <MdLocalLibrary />
          기술 스택
        </p>
        <p>
          {checkIsNoData(oneEmployeeSpecQuery.data?.skillSet) ? null : (
            <div className={`${MS.displayFlex}`}>
              {oneEmployeeSpecQuery.data?.skillSet.map((skill: string) => (
                <p className={`${S.keyword} ${MS.Mr10} ${S[skill]}`}>{skill}</p>
              ))}
            </div>
          )}
        </p>
      </div>

      <div className={S.rowInfo}>
        <p className={S.rowTitle}>
          <BiSolidPencil />
          평가 점수
        </p>
        <div className={S.scoreRow}>
          <div className={S.scoreColumn}>
            <p className={S.scoreTitle}>KPI</p>
            <p className={S.scoreTitle}>{oneEmployeeSpecQuery.data?.kpiScore}</p>
          </div>
          <div className={S.scoreColumn}>
            <p className={S.scoreTitle}>동료평가</p>
            <p className={S.scoreTitle}>{oneEmployeeSpecQuery.data?.getPeerEvaluationScore}</p>
          </div>
        </div>
      </div>

      <div className={S.rowInfo}>
        <p className={S.rowTitle}>
          <FaSearch />
          개인 성향
        </p>
        <p className={S.keyword}>{oneEmployeeSpecQuery.data?.personalType}</p>
      </div>

      <div className={S.rowInfo}>
        <p className={S.rowTitle}>
          <PiProjectorScreenChartFill />
          프로젝트
        </p>
        <div className={S.rowContent}>
          {checkIsNoData(oneEmployeeSpecQuery.data?.projects) ? (
            <NoProjectBlocks />
          ) : (
            <ProjectBlocks projects={oneEmployeeSpecQuery.data?.projects} />
          )}
        </div>
      </div>

      <div className={S.rowInfo}>
        <p className={S.rowTitle}>
          <BiSolidReport />
          이력
        </p>
        {checkIsNoData(oneEmployeeSpecQuery.data?.pastProjects) ? (
          <NoPastProjectBlocks />
        ) : (
          <PastProjectBlocks pastProjects={oneEmployeeSpecQuery.data?.pastProjects} />
        )}
      </div>
    </div>
  );
};

const NoProjectBlocks = () => {
  return (
    <div className={S.noProjectContainer}>
      <p>수행한 프로젝트가 없습니다.</p>
    </div>
  );
};

const ProjectBlocks = ({ projects }: { projects: projectInfoType[] }) => {
  const navigate = useNavigate();

  return (
    <div>
      {projects.map((project) => {
        return (
          <div
            className={S.projectBlock}
            onClick={() => {
              navigate(`/project/${project.id}`);
            }}>
            <div className={S.projectTitleContainer}>
              <p className={S.projectTitle}>
                <BsCursorFill />
                {project.projectName}
              </p>
              <p
                className={`${S.projectStatus} ${
                  S[checkDate(project.startDate, project.mvpDate)]
                }`}>
                {checkDate(project.startDate, project.mvpDate) === 'pending'
                  ? '진행 예정'
                  : checkDate(project.startDate, project.mvpDate) === 'running'
                  ? '진행 중'
                  : '종료'}
              </p>
            </div>
            <div className={S.projectDate}>
              <p className={S.projectDescription}>{project.startDate}</p>
              <p>~</p>
              <p className={S.projectDescription}>{project.mvpDate}</p>
            </div>
          </div>
        );
      })}
    </div>
  );
};

const NoPastProjectBlocks = () => {
  return (
    <div className={S.noProjectContainer}>
      <p>이력에 프로젝트 정보가 없습니다.</p>
    </div>
  );
};

interface PastProjectInfo {
  id: number;
  projectName: string;
  description: string;
}

const PastProjectBlocks = ({ pastProjects }: { pastProjects: PastProjectInfo[] }) => {
  return (
    <div>
      {pastProjects.map((project) => {
        return (
          <div className={S.pastProjectBlock}>
            <p className={S.pastProjectTitle}>
              <FaDotCircle />
              {project.projectName}
            </p>
            <p className={S.pastProjectDescription}>{project.description}</p>
          </div>
        );
      })}
    </div>
  );
};
