import { useState } from 'react';
import { useParams } from 'react-router-dom';
import SprintBurndownChart from './SprintBurndown';
import ObjectiveAchievementChart from './ObjectiveAchievement';
import OtherMetricsCharts from './ProjectEvaluation';
import { MS } from 'styles';
import S from './Evaluation.module.scss';



export default function Evaluation() {
  const { id, memberId } = useParams();
  const [showChart, setShowChart] = useState(true);
  const [selectedMemberId, setSelectedMemberId] = useState<number>();

  const handleMemberChange = (memberId: number) => {
    setSelectedMemberId(memberId);
  };

  return (
    <div className={MS.container}>
      <div className={MS.content}>
        <div className={S.evaluationContainer}>
          <div className={S.topCharts}>
            <div className={S.chartSection}>
              <div className={`${S.ChartTitle}`}>
                <h2>목적 달성률</h2>
              </div>
                <ObjectiveAchievementChart projectId={id!} />
            </div>
            <div className={S.chartSection}>
              <h2>스프린트 번다운</h2>
              <SprintBurndownChart projectId={id!} memberId={selectedMemberId!} />
            </div>
          </div>
          <div className={S.chartSection}>
            <OtherMetricsCharts projectId={id!} 
            onMemberChange={handleMemberChange}/>
          </div>
        </div>
      </div>
    </div>
  );
}