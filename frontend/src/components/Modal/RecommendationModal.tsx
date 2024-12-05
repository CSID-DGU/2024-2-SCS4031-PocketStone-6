import { useMemberInfoById } from 'hooks/useMemberInfoById';
import { useState } from 'react';
import { FaXmark } from 'react-icons/fa6';
import { BS, ChS, CS, MS, TS } from 'styles';
import { getMemberIdListFromRecommend } from 'utils/parseList';
import { RadarChart, PolarGrid, PolarAngleAxis, Radar, ResponsiveContainer } from 'recharts';
import LoadingModal from 'components/Modal/LoadingModal';

export default function RecommendationModal({
  data,
  setData,
  setSelectedMemberList,
}: {
  data: any;
  setData: React.Dispatch<React.SetStateAction<any>>;
  setSelectedMemberList: React.Dispatch<React.SetStateAction<number[]>>;
}) {
  const [page, setPage] = useState('0');
  if (!data || !data.teams || !data.teams[Number(page)]) {
    return <LoadingModal />;
  }
  const currentTeam = data.teams[Number(page)];
  const radarData = [
    { metric: '종합 점수', value: currentTeam.final_score },
    { metric: '프로젝트 적합도', value: currentTeam.project_fit_score },
    { metric: '성격 유사도', value: currentTeam.scaled_personality_similarity },
    { metric: 'KPI 평가점수', value: currentTeam.kpi_score },
    { metric: '동료 평가', value: currentTeam.peer_evaluation_score },
    { metric: '기술 점수', value: currentTeam.skill_score },
  ];

  const barData = radarData.map((item) => ({
    ...item,
    percentage: (item.value * 100).toFixed(1),
  }));

  return (
    <div className={MS.modalBackground}>
      <div className={MS.recommendModalBox}>
        <button
          className={BS.ModalBtn}
          onClick={() => {
            setData({});
          }}>
          <FaXmark />
        </button>
        <p className={`${TS.title} ${MS.Mb20}`}>추천 결과</p>

        <div className={`${MS.Mb20}`}>
          <div className={MS.recommendBtns}>
            <button
              className={page === '0' ? BS.YellowBtn : BS.WhiteBtn}
              onClick={() => {
                setPage('0');
              }}>
              1순위
            </button>
            <button
              className={page === '1' ? BS.YellowBtn : BS.WhiteBtn}
              onClick={() => {
                setPage('1');
              }}>
              2순위
            </button>
            <button
              className={page === '2' ? BS.YellowBtn : BS.WhiteBtn}
              onClick={() => {
                setPage('2');
              }}>
              3순위
            </button>
          </div>

          <div className={ChS.chartsContainer}>
            <div className={ChS.radarChart}>
              <ResponsiveContainer width="100%" height={300}>
                <RadarChart data={radarData.filter((item) => item.metric !== '종합 점수')}>
                  <PolarGrid />
                  <PolarAngleAxis dataKey="metric" />
                  <Radar
                    name="팀 점수"
                    dataKey="value"
                    fill="#FFB800"
                    fillOpacity={0.7}
                    stroke="#FFB800"
                  />
                </RadarChart>
              </ResponsiveContainer>
            </div>

            <div className={ChS.scoreDetails}>
              {barData
                .filter((item) => item.metric !== '종합 점수')
                .map((item, index) => (
                  <div key={index} className={ChS.scoreItem}>
                    <span className={ChS.scoreLabel}>{item.metric}</span>
                    <span className={ChS.scoreValue}>{item.percentage}점</span>
                  </div>
                ))}

              {barData
                .filter((item) => item.metric === '종합 점수')
                .map((item, index) => (
                  <div key={index} className={`${ChS.highlightedScore}`}>
                    <span className={`${ChS.highlightedLabel}`}>{item.metric}</span>
                    <span className={`${ChS.highlightedValue}`}>{item.percentage}점</span>
                  </div>
                ))}
            </div>
          </div>
        </div>

        <MemberList list={data.teams[Number(page)].team_indices} />
        <div className={`${MS.displayFlex} ${MS.flexRight}`}>
          <button
            className={`${BS.YellowBtn}`}
            onClick={() => {
              setSelectedMemberList((prevList) => [
                ...prevList,
                ...getMemberIdListFromRecommend(data)[Number(page)],
              ]);
              setData({});
            }}>
            추천 리스트 적용
          </button>
        </div>
      </div>
    </div>
  );
}

export const MemberList = ({ list }: { list: number[] }) => {
  return (
    <div className={MS.Mb10}>
      <div className={CS.contentTitle}>
        <div className={MS.displayFlex}>
          <div className={`${CS.category} ${MS.flexOne}`}>관리번호</div>
          <div className={`${CS.category} ${MS.flexOne}`}>이름</div>
          <div className={`${CS.category} ${MS.flexOne}`}>부서</div>
          <div className={`${CS.category} ${MS.flexOne}`}>직책</div>
          <div className={`${CS.category} ${MS.flexTwo}`}>역할</div>
        </div>
      </div>
      <div className={CS.modalContentBox}>
        {list.map((employeeId) => (
          <MemberBlock employeeId={employeeId} key={employeeId} />
        ))}
      </div>
    </div>
  );
};

const MemberBlock = ({ employeeId }: { employeeId: number }) => {
  const { memberAllInfo } = useMemberInfoById(employeeId);

  return (
    <div className={CS.container}>
      <div className={CS.card}>
        <div className={CS.canClickPart}>
          <div className={`${CS.category} ${MS.flexOne}`}>{memberAllInfo.staffId}</div>
          <div className={`${CS.category} ${MS.flexOne}`}>{memberAllInfo.name}</div>
          <div className={`${CS.category} ${MS.flexOne}`}>{memberAllInfo.department}</div>
          <div className={`${CS.category} ${MS.flexOne}`}>{memberAllInfo.position}</div>
          <div className={`${CS.category} ${MS.flexTwo}`}>{memberAllInfo.role}</div>
        </div>
      </div>
    </div>
  );
};
