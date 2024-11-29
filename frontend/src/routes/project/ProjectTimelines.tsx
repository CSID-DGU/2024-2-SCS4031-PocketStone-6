import { useProjectDetailInfoQuery } from 'hooks/useProjectDetailInfoQuery';
import { useNavigate, useParams } from 'react-router-dom';
import { BS, IS, MS, TS } from 'styles';
import S from './ProjectTimelines.module.scss';
import { useEffect, useState } from 'react';
import { modifyProjectTimelines } from 'api/projects/modifyProjectTimeline';
import { createProjectTimelines } from 'api/projects/createProjectTimelines';
import { FaTrash } from 'react-icons/fa';

export default function ProjectTimelines() {
  const { id } = useParams();
  const { timelinesQuery } = useProjectDetailInfoQuery(Number(id));
  const [timelines, setTimelines] = useState<TimelineData[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    setTimelines(timelinesQuery?.data || []);
  }, [timelinesQuery.data]);

  const handleUpdateTimeline = (updatedTimeline: TimelineData) => {
    setTimelines((prevTimelines) =>
      prevTimelines.map((timeline) =>
        timeline.id === updatedTimeline.id ? updatedTimeline : timeline
      )
    );
  };

  const handleSaveChanges = () => {
    modifyProjectTimelines(Number(id), timelines, navigate);
  };

  return (
    <div className={MS.container}>
      <div className={MS.content}>
        <div className={MS.contentTitle}>
          <p>프로젝트 타임라인 수정</p>
        </div>
        <div className={MS.contentBox}>
          {timelines.map((timeline) => (
            <TimelineModifyBlock
              key={timeline.id}
              timeline={timeline}
              onUpdateTimeline={handleUpdateTimeline}
            />
          ))}
          <div className={S.btnContainer}>
            <button
              className={BS.YellowBtn}
              onClick={() => {
                createProjectTimelines(Number(id), timelines.length, navigate);
              }}>
              새 스프린트 생성
            </button>
            <button className={BS.YellowBtn} onClick={handleSaveChanges}>
              수정완료 및 저장
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

interface TimelineModifyBlockProps {
  timeline: TimelineData;
  onUpdateTimeline: (updatedTimeline: TimelineData) => void;
}

const TimelineModifyBlock = ({ timeline, onUpdateTimeline }: TimelineModifyBlockProps) => {
  const sprintOrder = timeline.sprintOrder;
  const [sprintContent, setSprintContent] = useState(timeline.sprintContent);
  const [sprintDurationWeek, setSprintDurationWeek] = useState(timeline.sprintDurationWeek);

  const handleUpdate = () => {
    onUpdateTimeline({
      ...timeline,
      sprintOrder,
      sprintContent,
      sprintDurationWeek,
    });
  };

  return (
    <div className={S.blockContainer}>
      <div className={`${MS.displayFlex} ${MS.flexSpace}`}>
        <p className={TS.title}>스프린트 {sprintOrder}</p>
        <button className={BS.removeBtn}>
          <FaTrash />
        </button>
      </div>
      <input
        className={`${IS.textInput} ${MS.Mr10} ${MS.Mb10}`}
        value={sprintDurationWeek}
        placeholder="스프린트 기간"
        type="text"
        onChange={(e) => setSprintDurationWeek(Number(e.target.value))}
        onBlur={handleUpdate}
      />
      주
      <input
        className={`${IS.textInput} ${MS.width100}`}
        value={sprintContent}
        placeholder="스프린트 내용"
        type="text"
        onChange={(e) => setSprintContent(e.target.value)}
        onBlur={handleUpdate}
      />
    </div>
  );
};
