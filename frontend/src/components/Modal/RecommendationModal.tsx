import { FaXmark } from 'react-icons/fa6';
import { BS, MS, TS } from 'styles';

export default function RecommendationModal({
  data,
  setData,
}: {
  data: any; // 추천 데이터 타입
  setData: React.Dispatch<React.SetStateAction<any>>;
}) {
  return (
    <div className={MS.modalBackground}>
      <div className={MS.modalBox}>
        <button
          className={BS.ModalBtn}
          onClick={() => {
            setData({});
          }}>
          <FaXmark />
        </button>
        <p className={TS.title}>추천 결과</p>
        <p>{JSON.stringify(data)}</p>
      </div>
    </div>
  );
}
