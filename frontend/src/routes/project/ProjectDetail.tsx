import { useNavigate, useParams } from 'react-router-dom';
import { BS, MS, TS } from 'styles';
import S from './ProjectDetail.module.scss';
import { useProjectDetailInfoQuery } from 'hooks/useProjectDetailInfoQuery';
import { useProjectMemberQuery } from 'hooks/useProjectMemberQuery';
import { createProjectCharter } from 'api/projects/createProjectCharter';
import { createProjectTimelines } from 'api/projects/createProjectTimelines';
import { checkIsNoData } from 'utils/checkIsNoData';
import { NO_CHARTER_OR_TIMELINES } from 'constants/errorMessage';

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
            {checkIsNoData(charterQuery.data) ? (
              <button
                className={BS.YellowBtn}
                onClick={() => {
                  createProjectCharter(Number(id), navigate);
                }}>
                프로젝트 차터 생성
              </button>
            ) : (
              <button
                className={BS.WhiteBtn}
                onClick={() => {
                  navigate(`/project/${id}/charter`);
                }}>
                프로젝트 차터 수정
              </button>
            )}
          </div>
          <div className={`${MS.contentBox} ${S.contentBox}`}>
            <p>{JSON.stringify(basicInfoQuery.data)}</p>

            {/* 차터 관련 정보 */}
            <p>{JSON.stringify(charterQuery.data)}</p>
          </div>
        </div>
        <div className={MS.content}>
          <div className={`${MS.contentTitle} ${MS.displayFlex} ${MS.flexSpace}`}>
            <p>타임라인</p>
            {checkIsNoData(timelinesQuery.data) ? (
              <button
                className={BS.YellowBtn}
                onClick={() => {
                  createProjectTimelines(Number(id), 0, navigate);
                }}>
                타임라인 생성
              </button>
            ) : (
              <button
                className={BS.WhiteBtn}
                onClick={() => {
                  navigate(`/project/${id}/timelines`);
                }}>
                타임라인 수정
              </button>
            )}
          </div>
          <div className={`${MS.contentBox} ${S.contentBox}`}>
            {checkIsNoData(timelinesQuery.data) ? null : (
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
          <p>{JSON.stringify(memberQuery.data)}</p>
        </div>
      </div>
    </div>
  );
}

const TimelinesList = ({ timelinesList }: { timelinesList: TimelineData[] }) => {
  return (
    <div className={S.timelineListContainer}>
      {timelinesList.map(({ sprintOrder, sprintContent, sprintDurationWeek }) => {
        return (
          <div className={S.timelineContainer}>
            <p className={`${TS.smallTitle} ${MS.Mb5}`}>
              스프린트 {sprintOrder}({sprintDurationWeek}주)
            </p>
            <p>🚩 {sprintContent}</p>
          </div>
        );
      })}
    </div>
  );
};
