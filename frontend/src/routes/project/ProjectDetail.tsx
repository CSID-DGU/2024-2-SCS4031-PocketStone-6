import { useNavigate, useParams } from 'react-router-dom';
import { BS, CS, MS, TS } from 'styles';
import S from './ProjectDetail.module.scss';
import { useProjectDetailInfoQuery } from 'hooks/useProjectDetailInfoQuery';
import { useProjectMemberQuery } from 'hooks/useProjectMemberQuery';
import { checkIsNoData } from 'utils/checkIsNoData';
import { NO_CHARTER_OR_TIMELINES } from 'constants/errorMessage';
import { MdDateRange } from 'react-icons/md';

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
            <button className={BS.YellowBtn}>차터 버튼</button>
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
              <p>{JSON.stringify(charterQuery.data)}</p>
            )}
          </div>
        </div>
        <div className={MS.content}>
          <div className={`${MS.contentTitle} ${MS.displayFlex} ${MS.flexSpace}`}>
            <p>타임라인</p>
            <button className={BS.YellowBtn}>타임라인 버튼</button>
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
            <p>{JSON.stringify(memberQuery.data)}</p>
          )}
        </div>
      </div>
    </div>
  );
}

const TimelinesList = ({ timelinesList }: { timelinesList: TimelineData[] }) => {
  return (
    <div className={S.timelineListContainer}>

      <p>{JSON.stringify(timelinesList)}</p>

      {timelinesList.map(
        ({ sprintOrder, sprintContent, sprintStartDate, sprintEndDate, requiredManmonth }) => {
          return (
            <div className={S.timelineContainer}>
              <p className={`${TS.smallTitle} ${MS.Mb5}`}>스프린트 {sprintOrder}</p>
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
