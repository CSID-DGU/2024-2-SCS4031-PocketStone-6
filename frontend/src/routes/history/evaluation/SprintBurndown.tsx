import React from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { useSprintEvaluationQuery } from '../../../hooks/useSprintEvaluationQuery';

interface SprintEvaluation {
  id: number;
  timelineId: number;
  burndownRate: number;
}

interface Props {
  projectId: string | number;
  memberId: string | number;
}

const SprintBurndownChart: React.FC<Props> = ({ projectId, memberId }) => {
  const sprintQuery = useSprintEvaluationQuery(projectId, memberId);

  if (sprintQuery.isLoading) return <div>로딩중</div>;
  if (sprintQuery.isError) return <div>에러</div>;
  if (!sprintQuery.data) return <div>데이터 없음</div>;

  const sprintData = Array.isArray(sprintQuery.data) ? sprintQuery.data : [];
  const totalSprints = sprintData.length;
  const pointsPerSprint = 100;
  const totalPoints = totalSprints * pointsPerSprint;
  const idealDecreasePerSprint = totalPoints / totalSprints;

  const chartData = sprintData.reduce((acc, sprint, index) => {
    const completedThisSprint = (sprint.burndownRate / 100) * pointsPerSprint;
    const previousCompleted = index > 0 ? acc[index - 1].totalCompleted : 0;
    const totalCompleted = previousCompleted + completedThisSprint;
    const remainingWork = totalPoints - totalCompleted;

    const idealBurndown = totalPoints - (idealDecreasePerSprint * (index + 1));

    acc.push({
      sprintIndex: index + 1, 
      remainingWork,
      idealBurndown,
      totalCompleted,
      completedThisSprint,
      burndownRate: sprint.burndownRate
    });

    return acc;
  }, [] as Array<{
    sprintIndex: number;
    remainingWork: number;
    idealBurndown: number;
    totalCompleted: number;
    completedThisSprint: number;
    burndownRate: number;
  }>);

  const completeChartData = [
    { sprintIndex: 0, remainingWork: null, idealBurndown: totalPoints },
    ...chartData
  ];

  const getDotSize = (sprintCount: number) => {
    if (sprintCount <= 5) return { dot: 4, activeDot: 6 };
    if (sprintCount <= 10) return { dot: 3, activeDot: 5 };
    return { dot: 2, activeDot: 4 };
  };
  
  const { dot, activeDot } = getDotSize(totalSprints);

  return (
    <div className="bg-white rounded-lg shadow-lg p-4">
      <h2 className="text-xl font-bold mb-4"> </h2>
      <ResponsiveContainer width="100%" height={400}>
        <LineChart data={completeChartData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis 
            dataKey="sprintIndex"
            type="number"
            domain={[0, totalSprints]}
            tickFormatter={(value) => value === 0 ? 'Start' : `Sprint ${value}`}
            interval={0}
            allowDataOverflow={true}
            padding={{ left: 20, right: 40 }}
            ticks={Array.from({ length: totalSprints + 1 }, (_, i) => i)} 
          />
          <YAxis 
            domain={[0, totalPoints]}
            label={{ value: 'Story Points', angle: -90, position: 'insideLeft' }}
          />
          <Tooltip 
            formatter={(value: number, name: string, props: any) => {
              if (name === 'Ideal Burndown') {
                return [`${value.toFixed(0)} points`, name];
              }
              const { payload } = props;
              return [
                `${value.toFixed(0)} points`,
                'Actual Burndown'
              ];
            }}
            labelFormatter={(label) => label === 0 ? 'Start' : `Sprint ${label}`}
          />
          <Legend />
          <Line 
            type="monotone" 
            dataKey="idealBurndown" 
            stroke="#ff7300" 
            strokeDasharray="5 5"
            name="Ideal Burndown"
            dot={false}
          />
          <Line 
            type="monotone" 
            dataKey="remainingWork"
            stroke="#8884d8" 
            strokeWidth={2}
            name="Actual Burndown"
            connectNulls={true}
            dot={{ r: dot }}
            activeDot={{ r: activeDot }}
          />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
};

export default SprintBurndownChart;