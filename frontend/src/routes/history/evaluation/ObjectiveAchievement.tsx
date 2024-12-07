import React from 'react';
import { useObjectiveEvaluationQuery } from '../../../hooks/useObjectiveEvaluationQuery';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';

interface ObjectiveAchievementProps {
  projectId: string | number;
}

interface ObjectiveData {
    objectiveName: string;
    achieveRate: number;
    objectiveId: string;
    [key: string]: any;
  }
  
  

const ObjectiveAchievementChart: React.FC<ObjectiveAchievementProps> = ({ projectId }) => {
  const objectiveQuery = useObjectiveEvaluationQuery(projectId);

  const BarData = (objectiveQuery.data || []).map((item: ObjectiveData) => ({
    ...item,
    achieveRate: item.achieveRate,
    objectiveName:item.objectiveName,
    remaining: 100 - item.achieveRate
  }));

  const getBarColor = (value: number) => {
    if (value === 100) return '#0052CC';  
    if (value >= 90) return '#87CEEB';    
    if (value >= 75) return '#48BB78';    
    return '#F56565';                     
  };

  if (objectiveQuery.isLoading) {return <div>로딩중</div>}


  return (
    <div className="w-full bg-white rounded-lg shadow">
      <div className="p-6">
        <h2 className="text-2xl font-bold text-gray-800 mb-6"> </h2>
        <ResponsiveContainer width="100%" height={400}>
          <BarChart 
            data={BarData || []} 
            margin={{ top: 20, right: 30, left: 20, bottom: 20 }}
            barSize={30}
          >
            <CartesianGrid strokeDasharray="3 3" stroke="#f0f0f0" />
            <XAxis
                dataKey="objectiveId"
                tickFormatter={(_, index) => String(index + 1)}  
            />
            <YAxis
              domain={[0, 100]}
              tick={{ fill: '#6b7280' }}
              tickLine={{ stroke: '#6b7280' }}
              tickFormatter={(value) => `${value}%`}
            />
            <Tooltip
            content={({ active, payload }) => {
              if (active && payload && payload.length) {
                const data = payload[0].payload;
                return (
                  <p>
                    {`${data.objectiveName} ${payload[0].value}%`}
                  </p>
                );
              }
              return null;
            }}
            />

            <Bar
              dataKey="achieveRate"
              name=" "
              radius={[4, 4, 0, 0]}
              stackId="a"
              fill="#ffffff"
              data={BarData.map((item: ObjectiveData) => ({
                ...item,
                fill: getBarColor(item.achieveRate)
              }))}
              
            />
            <Bar
              dataKey="remaining"
              name=" "
              radius={[4, 4, 0, 0]}
              stackId="a"
              fill="#e2dcdc" 
              stroke="#E5E7EB" 
            />
          </BarChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
};

export default ObjectiveAchievementChart;