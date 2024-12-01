import { TimelinesInput } from 'components/Input/TimelinesInput';
import { useProjectDetailInfoQuery } from 'hooks/useProjectDetailInfoQuery';
import { useTimelinesState } from 'hooks/useTimelinesState';
import { FaPlus } from 'react-icons/fa';
import { useParams } from 'react-router-dom';
import { BS, MS } from 'styles';
import { addTimelines } from 'utils/addTimelines';
import { handleTimelinesChange } from 'utils/handleTimelinesContent';

export default function ProjectTimelines() {
  const { id } = useParams();
  const { timelinesContent, setTimelinesContent } = useTimelinesState(Number(id));
  const { basicInfoQuery } = useProjectDetailInfoQuery(Number(id));

  return (
    <div className={MS.container}>
      <div className={MS.content}>
        <div className={`${MS.contentTitle} ${MS.displayFlex} ${MS.flexSpace}`}>
          <p>{id} 프로젝트 타임라인</p>
          <div className={MS.displayFlex}>
            <button
              className={BS.addBtn}
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
            <button
              className={BS.YellowBtn}
              onClick={() => {
                console.log(timelinesContent);
              }}>
              값 확인
            </button>
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
