import { useProjectDetailInfoQuery } from 'hooks/useProjectDetailInfoQuery';
import { useNavigate, useParams } from 'react-router-dom';
import { BS, IS, MS } from 'styles';
import S from './ProjectTimelines.module.scss';
import { useState } from 'react';
import { modifyProjectTimelines } from 'api/projects/modifyProjectTimeline';
import { createProjectTimelines } from 'api/projects/createProjectTimelines';

export default function ProjectTimelines() {
  const { id } = useParams();
  const { timelinesQuery } = useProjectDetailInfoQuery(Number(id));
  const [timelines, setTimelines] = useState<TimelineData[]>(timelinesQuery?.data || []);
  const navigate = useNavigate();

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
          <button
            className={BS.YellowBtn}
            onClick={() => {
              createProjectTimelines(Number(id), navigate);
            }}>
            새 스프린트 생성
          </button>
          <button className={BS.YellowBtn} onClick={handleSaveChanges}>
            수정
          </button>
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
  const [sprintOrder, setSprintOrder] = useState(timeline.sprintOrder);
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
      <input
        className={IS.textInput}
        value={sprintOrder}
        placeholder="스프린트 순서"
        type="text"
        onChange={(e) => setSprintOrder(Number(e.target.value))}
        onBlur={handleUpdate}
      />
      <input
        className={IS.textInput}
        value={sprintContent}
        placeholder="스프린트 내용"
        type="text"
        onChange={(e) => setSprintContent(e.target.value)}
        onBlur={handleUpdate}
      />
      <input
        className={IS.textInput}
        value={sprintDurationWeek}
        placeholder="스프린트 기간"
        type="text"
        onChange={(e) => setSprintDurationWeek(Number(e.target.value))}
        onBlur={handleUpdate}
      />
    </div>
  );
};
