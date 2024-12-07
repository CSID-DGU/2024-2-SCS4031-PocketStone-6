import React from 'react';
import { useParams } from 'react-router-dom';
import SprintBurndownChart from './SprintBurndown';
import ObjectiveAchievementChart from './ObjectiveAchievement';
import OtherMetricsCharts from './ProjectEvaluation';
import { MS } from 'styles';
import S from './Evaluation.module.scss';

export default function Evaluation() {
  const { id } = useParams();

  return (
    <div className={MS.container}>
      <div className={MS.content}>
        <div className={S.evaluationContainer}>
          <div className={S.topCharts}>
            <div className={S.chartSection}>
              <h2 className={MS.ContentTitle}>목적 달성률</h2>
              <ObjectiveAchievementChart projectId={id!} />
            </div>
            <div className={S.chartSection}>
              <h2 className={MS.ContentTitle}>스프린트 번다운</h2>
              <SprintBurndownChart projectId={id!} />
            </div>
          </div>
          <div className={S.chartSection}>
            <OtherMetricsCharts projectId={id!} />
          </div>
        </div>
      </div>
    </div>
  );
}