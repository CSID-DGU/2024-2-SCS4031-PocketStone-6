import { useMemberInfoById } from 'hooks/useMemberInfoById';
import { useState } from 'react';
import { FaXmark } from 'react-icons/fa6';
import { BS, CS, MS, TS } from 'styles';
import { getMemberIdListFromRecommend } from 'utils/parseList';

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
        <MemberList list={data.memberList} />
        <div className={`${MS.displayFlex} ${MS.flexRight}`}>
          <button
            className={`${BS.YellowBtn}`}
            onClick={() => {
              setSelectedMemberList((prevList) => [
                ...prevList,
                ...getMemberIdListFromRecommend(data),
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

export const MemberList = ({ list }: { list: { employeeId: number; position: string }[] }) => {
  return (
    <div className={MS.Mb20}>
      <div className={CS.contentTitle}>
        <div className={MS.displayFlex}>
          <div className={`${CS.category} ${MS.flexOne}`}>관리번호</div>
          <div className={`${CS.category} ${MS.flexOne}`}>이름</div>
          <div className={`${CS.category} ${MS.flexOne}`}>부서</div>
          <div className={`${CS.category} ${MS.flexOne}`}>직책</div>
          <div className={`${CS.category} ${MS.flexTwo}`}>역할</div>
        </div>
      </div>
      <div className={CS.contentBox}>
        {list.map(({ employeeId }) => (
          <MemberBlock employeeId={employeeId} />
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
