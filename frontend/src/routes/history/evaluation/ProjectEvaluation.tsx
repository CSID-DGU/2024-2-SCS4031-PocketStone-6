import React, { useState } from 'react';
import { RadialBarChart, RadialBar, BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer, Cell } from 'recharts';
import { useQualityEvaluationQuery } from '../../../hooks/useQualityEvaluationQuery';
import { useResourceEvaluationQuery } from '../../../hooks/useResourceEvaluationQuery';
import { usePeerEvaluationQuery } from '../../../hooks/usePeerEvaluationQuery';
import S from './Evaluation.module.scss';
import { MS } from 'styles';
import TeamMemberSlider from './TeamMemberSlider';

interface OtherMetricsProps {
  projectId: string | number;
}

interface PeerEvaluation {
  memberId: number;
  performanceScore: number;
  interpersonalScore: number;
  expertiseScore: number;
  responsibilityScore: number;
}

const OtherMetricsCharts: React.FC<OtherMetricsProps> = ({ projectId }) => {
  const [currentMemberId, setCurrentMemberId] = useState<number | null>(null);
  const qualityQuery = useQualityEvaluationQuery(projectId);
  const resourceQuery = useResourceEvaluationQuery(projectId);
  const peerQuery = usePeerEvaluationQuery(projectId);

  if (qualityQuery.isLoading || resourceQuery.isLoading || peerQuery.isLoading) {
    return <div>Loading...</div>;
  }

  return (
    <div className={S.bottomMetrics}>
      <div className={S.metricCard}>
        <h2 className={MS.ContentTitle}>프로젝트 지표</h2>
        <ResponsiveContainer width="100%" height={300}>
          <RadialBarChart
            innerRadius="20%"
            outerRadius="90%"
            data={[
              { name: '테스트 커버리지', value: qualityQuery.data?.testCoverage, fill: '#FF8042' },
              { name: '품질', value: qualityQuery.data?.productQuality, fill: '#00C49F' },
              { name: '성능', value: qualityQuery.data?.performance, fill: '#0088FE' },
              { name: '안정성', value: qualityQuery.data?.reliability, fill: '#FFBB28' }
            ].sort((a, b) => b.value - a.value)}
            cx="50%"
            cy="50%"
            startAngle={90}
            endAngle={-270}
          >
            <RadialBar
              background
              dataKey="value"
              cornerRadius={15}
              label={{
                position: 'insideStart',
                fill: '#fff',
                fontSize: 12
              }}
            />
            <Legend
              iconSize={10}
              layout="horizontal"
              verticalAlign="bottom"
              wrapperStyle={{
                paddingTop: '20px'
              }}
            />
            <Tooltip 
              content={({ active, payload }) => {
                if (active && payload && payload.length) {
                  const data = payload[0].payload;
                  return (
                    <p>
                      {`${data.name} ${payload[0].value}%`}
                    </p>
                  );
                }
                return null;
              }}
            />
          </RadialBarChart>
        </ResponsiveContainer>
      </div> 

      <div className={S.metricCard}>
        <TeamMemberSlider 
          projectId={Number(projectId)} 
          onMemberChange={setCurrentMemberId}
        />
      </div>

      <div className={S.metricCard}>
        <h2 className={S.chartTitle}>동료 평가</h2>
        <ResponsiveContainer width="100%" height={300}>
        <BarChart 
          layout="vertical" 
          data={peerQuery.data?.filter((item: PeerEvaluation) => item.memberId === currentMemberId).map((item: PeerEvaluation) => ([
            {
              name: '업무 성과',
              평가점수: item.performanceScore,
              fill: '#8884d8'  
            },
            {
              name: '대인 관계',
              평가점수: item.interpersonalScore,
              fill: '#82ca9d' 
            },
            {
              name: '전문 능력',
              평가점수: item.expertiseScore,
              fill: '#ffc658'  
            },
            {
              name: '책임 의식',
              평가점수: item.responsibilityScore,
              fill: '#ff8042'  
            }
          ]))[0] || []}
          margin={{ right: 10, left: 0, top: 10, bottom: 5 }}
        >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis type="number" domain={[25, 100]} />
          <YAxis type="category" dataKey="name" width={50} />
          <Tooltip 
          />
          <Bar 
            dataKey="평가점수" 
            fill="#8884d8"
            barSize={30}
            label={{ 
              position: 'right',
              fill: '#666',
              formatter: (평가점수: number) => `${평가점수}%`
            }}
          >
            <Cell fill="#8884d8" />
            <Cell fill="#82ca9d" />
            <Cell fill="#ffc658" />
            <Cell fill="#ff8042" />
          </Bar>
        </BarChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
};

export default OtherMetricsCharts;