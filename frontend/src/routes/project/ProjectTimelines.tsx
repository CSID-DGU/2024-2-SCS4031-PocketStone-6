import { postProjectTimelines } from 'api/projects/postProjectTimelines';
import { putProjectTimelines } from 'api/projects/putProjectTimelines';
import { TimelinesInput } from 'components/Input/TimelinesInput';
import { useProjectDetailInfoQuery } from 'hooks/useProjectDetailInfoQuery';
import { useTimelinesState } from 'hooks/useTimelinesState';
import { FaPlus } from 'react-icons/fa';
import { useNavigate, useParams } from 'react-router-dom';
import { BS, MS } from 'styles';
import { addTimelines } from 'utils/addTimelines';
import { checkIsNoData } from 'utils/checkIsNoData';
import { handleTimelinesChange } from 'utils/handleTimelinesContent';

export default function ProjectTimelines() {
  const { id } = useParams();
  const { timelinesContent, setTimelinesContent } = useTimelinesState(Number(id));
  const { timelinesQuery, basicInfoQuery } = useProjectDetailInfoQuery(Number(id));
  const navigate = useNavigate();

  return (
    <div className={MS.container}>
      <div className={MS.content}>
        <div className={`${MS.contentTitle} ${MS.displayFlex} ${MS.flexSpace}`}>
          <p>프로젝트 타임라인 수정</p>
          <div className={MS.displayFlex}>
            <button
              className={`${BS.addBtn} ${MS.Mr10}`}
              onClick={() => {
                setTimelinesContent(
                  addTimelines(
                    timelinesContent,
                    basicInfoQuery.data?.startDate,
                    basicInfoQuery.data?.mvpDate
                  )
                );
              }}>
              <FaPlus />
            </button>
            {checkIsNoData(timelinesQuery?.data) ? (
              <button
                className={BS.YellowBtn}
                onClick={() => {
                  if (checkIsNoData(timelinesContent)) {
                    alert('추가된 값이 아무것도 없습니다.');
                    return;
                  }
                  postProjectTimelines(Number(id), timelinesContent, navigate);
                }}>
                수정완료 및 저장
              </button>
            ) : (
              <button
                className={BS.YellowBtn}
                onClick={() => {
                  putProjectTimelines(Number(id), timelinesContent, navigate);
                }}>
                수정완료 및 저장
              </button>
            )}
          </div>
        </div>
        <div className={MS.contentBox}>
          {timelinesContent === undefined ? null : (
            <TimelinesInput
              timelinesList={[...timelinesContent.slice()]}
              onChange={handleTimelinesChange}
              content={timelinesContent}
              setContent={setTimelinesContent}
            />
          )}
        </div>
      </div>
    </div>
  );
}
