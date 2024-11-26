import { useParams } from 'react-router-dom';
import { useOneEmployeeInfoQuery } from '../../hooks/useOneEmployeeInfoQuery';
import { useOneEmployeeSpecQuery } from '../../hooks/useOneEmployeeSpecQuery';
import { MS } from 'styles';
import S from 'styles/EmployDetail.module.scss';
import { parsePhoneNumber } from 'utils/parsePhoneNumber';
import { FaPhoneAlt, FaSearch, FaQuestionCircle, FaDotCircle } from 'react-icons/fa';
import { IoIosMail } from 'react-icons/io';
import { BiSolidPencil, BiSolidReport } from 'react-icons/bi';
import { MdLocalLibrary, MdOutlineDateRange } from 'react-icons/md';
import { checkIsNoData } from 'utils/checkIsNoData';

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
                <p className={`${S.keyword} ${MS.Mr10}`}>{skill}</p>
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
          <BiSolidReport />
          프로젝트
        </p>
        {checkIsNoData(oneEmployeeSpecQuery.data?.pastProjects) ? (
          <NoProjectBlocks />
        ) : (
          <ProjectBlocks pastProjects={oneEmployeeSpecQuery.data?.pastProjects} />
        )}
      </div>
    </div>
  );
};

const NoProjectBlocks = () => {
  return (
    <div className={S.noProjectContainer}>
      <p>이전에 수행한 프로젝트가 없습니다.</p>
    </div>
  );
};

interface PastProjectInfo {
  id: number;
  projectName: string;
  description: string;
}

const ProjectBlocks = ({ pastProjects }: { pastProjects: PastProjectInfo[] }) => {
  return (
    <div>
      {pastProjects.map((project) => {
        return (
          <div className={S.projectBlock}>
            <p className={S.projectTitle}>
              <FaDotCircle />
              {project.projectName}
            </p>
            <p className={S.projectDescription}>{project.description}</p>
          </div>
        );
      })}
    </div>
  );
};
