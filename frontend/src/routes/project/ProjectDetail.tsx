import { useNavigate, useParams } from 'react-router-dom';
import { BS, CS, MS, TS } from 'styles';
import S from './ProjectDetail.module.scss';
import { useProjectDetailInfoQuery } from 'hooks/useProjectDetailInfoQuery';
import { useProjectMemberQuery } from 'hooks/useProjectMemberQuery';
import { checkIsNoData } from 'utils/checkIsNoData';
import { NO_CHARTER_OR_TIMELINES } from 'constants/errorMessage';
import { MdDateRange } from 'react-icons/md';
import { useMemberInfoById } from 'hooks/useMemberInfoById';

export default function ProjectDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { basicInfoQuery, charterQuery, timelinesQuery } = useProjectDetailInfoQuery(Number(id));
  const memberQuery = useProjectMemberQuery(Number(id));

  return (
    <div className={MS.container}>
      <div className={S.doubleContentDiv}>
        <div className={MS.content}>
          <div className={`${MS.contentTitle} ${MS.displayFlex} ${MS.flexSpace}`}>
            <p>프로젝트 정보</p>
            <button
              className={BS.YellowBtn}
              onClick={() => {
                navigate(`/project/${id}/charter`);
              }}>
              차터 버튼
            </button>
          </div>
          <div className={`${MS.contentBox} ${S.contentBox}`}>
            {/* 기본 정보 */}
            <p className={`${TS.smallTitle} ${MS.Mb5}`}>{basicInfoQuery.data?.projectName}</p>
            <p className={S.dateText}>
              <MdDateRange />
              {basicInfoQuery.data?.startDate} ~ {basicInfoQuery.data?.mvpDate}
            </p>

            {/* 차터 관련 정보 */}
            {checkIsNoData(charterQuery.data) ? (
              <NoCharter />
            ) : (
              <Charter charterData={charterQuery?.data} />
            )}
          </div>
        </div>
        <div className={MS.content}>
          <div className={`${MS.contentTitle} ${MS.displayFlex} ${MS.flexSpace}`}>
            <p>타임라인</p>
            <button
              className={BS.YellowBtn}
              onClick={() => {
                navigate(`/project/${id}/timelines`);
              }}>
              타임라인 버튼
            </button>
          </div>
          <div className={`${MS.contentBox} ${S.contentBox}`}>
            {checkIsNoData(timelinesQuery.data) ? (
              <NoTimelines />
            ) : (
              <TimelinesList timelinesList={timelinesQuery?.data} />
            )}
          </div>
        </div>
      </div>
      <div className={MS.content}>
        <div className={`${MS.contentTitle} ${MS.displayFlex} ${MS.flexSpace}`}>
          <p>프로젝트 인원 수정</p>
          {checkIsNoData(charterQuery.data) || checkIsNoData(timelinesQuery.data) ? (
            <button
              className={BS.unableBtn}
              title={NO_CHARTER_OR_TIMELINES}
              onClick={() => {
                alert(NO_CHARTER_OR_TIMELINES);
              }}>
              인원 수정
            </button>
          ) : (
            <button className={BS.WhiteBtn} onClick={() => navigate(`/project/${id}/member`)}>
              인원 수정
            </button>
          )}
        </div>
        <div className={MS.contentBox}>
          {checkIsNoData(memberQuery.data) ? (
            <NoMember />
          ) : (
            // JSON.stringify(memberQuery.data)
            <MemberList list={memberQuery.data} />
          )}
        </div>
      </div>
    </div>
  );
}

const TimelinesList = ({ timelinesList }: { timelinesList: TimelineData[] }) => {
  return (
    <div className={S.timelineListContainer}>
      {timelinesList.map(
        ({ sprintOrder, sprintContent, sprintStartDate, sprintEndDate, requiredManmonth }) => {
          return (
            <div className={S.timelineContainer}>
              <div className={S.timelineTitle}>
                <p className={`${TS.smallTitle} ${MS.Mr10}`}>스프린트 {sprintOrder}</p>
                <p className={`${TS.smallText}`}>Man-Month: {requiredManmonth}</p>
              </div>
              <p className={`${TS.smallText} ${MS.Mb5}`}>
                ({sprintStartDate} ~ {sprintEndDate})
              </p>
              <p>🚩 {sprintContent}</p>
            </div>
          );
        }
      )}
    </div>
  );
};

const NoCharter = () => {
  return (
    <div className={CS.notice}>
      <p>차터 정보를 아직 추가하지 않으셨어요.</p>
      <p>"프로젝트 차터 생성" 버튼을 눌러보세요!</p>
    </div>
  );
};

const Charter = ({ charterData }: { charterData: CharterContent }) => {
  return (
    <div className={S.charterContainer}>
      {/* <p>{JSON.stringify(charterData)}</p> */}
      <div className={S.charterSection}>
        <p className={`${TS.smallTitle} ${MS.Mb5}`}>목표</p>
        {charterData.objectives.map((data) => {
          return <ObjectiveBlock data={data} />;
        })}
      </div>
      <div className={S.charterSection}>
        <p className={`${TS.smallTitle} ${MS.Mb5}`}>원칙</p>
        {charterData.principles.map((data) => {
          return <PrincipleBlock data={data} />;
        })}
      </div>
      <div className={S.charterSection}>
        <p className={`${TS.smallTitle} ${MS.Mb5}`}>범위</p>
        {charterData.scopes.map((data) => {
          return <ScopeBlock data={data} />;
        })}
      </div>
      <div className={S.charterSection}>
        <p className={`${TS.smallTitle} ${MS.Mb5}`}>비전</p>
        {charterData.visions.map((data) => {
          return <VisionBlock data={data} />;
        })}
      </div>
      <div className={S.charterSection}>
        <p className={`${TS.smallTitle} ${MS.Mb5}`}>이해 관계자</p>
        {charterData.stakeholders.map((data) => {
          return <StakeholderBlock data={data} />;
        })}
      </div>
      <div className={S.charterSection}>
        <p className={`${TS.smallTitle} ${MS.Mb5}`}>위험 요소</p>
        {charterData.risks.map((data) => {
          return <RiskBlock data={data} />;
        })}
      </div>
    </div>
  );
};

const ObjectiveBlock = ({ data }: { data: Objectives }) => {
  return (
    <div className={S.charterBlock}>
      <p>{data.objectiveName}</p>
      <p>👉 {data.objectiveContent}</p>
    </div>
  );
};
const PrincipleBlock = ({ data }: { data: Principles }) => {
  return (
    <div className={S.charterBlock}>
      <p>{data.principleName}</p>
      <p>👉 {data.principleContent}</p>
    </div>
  );
};
const ScopeBlock = ({ data }: { data: Scopes }) => {
  return (
    <div className={S.charterBlock}>
      <p>{data.scopeName}</p>
      <p>👉 {data.scopeContent}</p>
    </div>
  );
};
const VisionBlock = ({ data }: { data: Visions }) => {
  return (
    <div className={S.charterBlock}>
      <p>{data.visionName}</p>
      <p>👉 {data.visionContent}</p>
    </div>
  );
};
const StakeholderBlock = ({ data }: { data: Stakeholders }) => {
  return (
    <div className={S.charterBlock}>
      <p>{data.stakeholderName}</p>
      <p>👉 {data.stakeholderContent}</p>
    </div>
  );
};
const RiskBlock = ({ data }: { data: Risks }) => {
  return (
    <div className={S.charterBlock}>
      <p>{data.riskName}</p>
      <p>👉 {data.riskContent}</p>
    </div>
  );
};

const NoTimelines = () => {
  return (
    <div className={CS.notice}>
      <p>타임라인 정보를 아직 추가하지 않으셨어요.</p>
      <p>"타임라인 생성" 버튼을 눌러보세요!</p>
    </div>
  );
};

const NoMember = () => {
  return (
    <div className={CS.notice}>
      <p>프로젝트에 포함된 인원이 없어요.</p>
    </div>
  );
};

const MemberList = ({ list }: { list: { employeeId: number; position: string }[] }) => {
  return (
    <div>
      <div className={CS.contentTitle}>
        <div className={MS.displayFlex}>
          <div className={`${CS.category} ${MS.flexOne}`}>관리번호</div>
          <div className={`${CS.category} ${MS.flexOne}`}>이름</div>
          <div className={`${CS.category} ${MS.flexOne}`}>부서</div>
          <div className={`${CS.category} ${MS.flexOne}`}>직책</div>
          <div className={`${CS.category} ${MS.flexTwo}`}>역할</div>
        </div>
      </div>
      <div className={CS.contentBox}>
        {list.map(({ employeeId }) => (
          <MemberBlock employeeId={employeeId} />
        ))}
      </div>
    </div>
  );
};

const MemberBlock = ({ employeeId }: { employeeId: number }) => {
  const { memberAllInfo } = useMemberInfoById(employeeId);
  const navigate = useNavigate();

  return (
    <div className={CS.container}>
      <div className={CS.card}>
        <div
          className={CS.canClickPart}
          onClick={() => {
            navigate(`/employee/${employeeId}`);
          }}>
          <div className={`${CS.category} ${MS.flexOne}`}>{memberAllInfo.staffId}</div>
          <div className={`${CS.category} ${MS.flexOne}`}>{memberAllInfo.name}</div>
          <div className={`${CS.category} ${MS.flexOne}`}>{memberAllInfo.department}</div>
          <div className={`${CS.category} ${MS.flexOne}`}>{memberAllInfo.position}</div>
          <div className={`${CS.category} ${MS.flexTwo}`}>{memberAllInfo.role}</div>
        </div>
      </div>
    </div>
  );
};
