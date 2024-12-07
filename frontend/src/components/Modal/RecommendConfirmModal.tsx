import { FaXmark } from 'react-icons/fa6';
import { BS, MS, TS } from 'styles';
import S from './RecommendConfirmModal.module.scss';
import { useState } from 'react';
import { PiProjectorScreenChartFill } from 'react-icons/pi';
import { IoMdPerson } from 'react-icons/io';
import { BiSolidPencil } from 'react-icons/bi';

interface RecommendConfirmModalProps {
  setModal: React.Dispatch<React.SetStateAction<boolean>>;
  confirmFunc: () => void;
}

export default function RecommendConfirmModal({
  setModal,
  confirmFunc,
}: RecommendConfirmModalProps) {
  const [category, setCategory] = useState(0);

  return (
    <div className={MS.modalBackground}>
      <div className={MS.recommendConfirmBox}>
        <button
          className={BS.ModalBtn}
          onClick={() => {
            setModal(false);
          }}>
          <FaXmark />
        </button>
        <p className={`${TS.title} ${MS.Mb20}`}>추천 가중치 선택</p>
        <div className={S.selectCardContainer}>
          <div className={category === 0 ? S.selectedCard : S.card} onClick={() => setCategory(0)}>
            <div className={S.cardTitleContainer}>
              <p className={S.cardTitle}>프로젝트 유사도 위주</p>
              <PiProjectorScreenChartFill size={120} />
            </div>
            <p className={S.cardDiscription}>
              수행한 프로젝트의 유사도를 위주로 팀원을 추천합니다.
            </p>
          </div>
          <div className={category === 1 ? S.selectedCard : S.card} onClick={() => setCategory(1)}>
            <div className={S.cardTitleContainer}>
              <p className={S.cardTitle}>기술점수 위주</p>
              <BiSolidPencil size={120} />
            </div>
            <p className={S.cardDiscription}>채점된 기술 점수를 위주로 팀원을 추천합니다.</p>
          </div>
          <div className={category === 2 ? S.selectedCard : S.card} onClick={() => setCategory(2)}>
            <div className={S.cardTitleContainer}>
              <p className={S.cardTitle}>개인 성향 위주</p>
              <IoMdPerson size={120} />
            </div>
            <p className={S.cardDiscription}>기록된 개인 성향을 위주로 팀원을 추천합니다.</p>
          </div>
        </div>
        <div className={`${MS.displayFlex} ${MS.flexRight}`}>
          <button className={BS.YellowBtn} onClick={confirmFunc}>
            확인
          </button>
        </div>
      </div>
    </div>
  );
}

// const Card = ({
//   id,
//   category,
//   setCategory,
// }: {
//   id: number;
//   category: number;
//   setCategory: React.Dispatch<React.SetStateAction<number>>;
// }) => {
//   return (
//     <div className={category === 0 ? S.selectedCard : S.card} onClick={() => setCategory(0)}>
//       <p>하나</p>
//     </div>
//   );
// };
