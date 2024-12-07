import React from 'react';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { useSprintEvaluationQuery } from '../../../hooks/useSprintEvaluationQuery';

interface SprintBurndownProps {
  projectId: string | number;
}

const SprintBurndownChart: React.FC<SprintBurndownProps> = ({ projectId }) => {
  const sprintQuery = useSprintEvaluationQuery(projectId);

  if (sprintQuery.isLoading) return <div>로딩중</div>;
  if (sprintQuery.isError) return <div>에러</div>;

  return (
    <div className="bg-white rounded-lg shadow-lg p-4">
      <h2 className="text-xl font-bold mb-4"></h2>
      <ResponsiveContainer width="100%" height={400}>
        <LineChart data={sprintQuery.data || []}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis 
          dataKey="timelineId"
          tickFormatter={(_, index) => String(index + 1)}
          />
          <YAxis domain={[0, 100]} />
          <Tooltip />
          <Line type="monotone" dataKey="burndownRate" stroke="#8884d8" strokeWidth={2} />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
};

export default SprintBurndownChart;