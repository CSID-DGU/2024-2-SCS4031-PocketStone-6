import { BS, MS } from 'styles';

interface RecommendConfirmModalProps {
  setModal: React.Dispatch<React.SetStateAction<boolean>>;
  confirmFunc: () => void;
}

export default function RecommendConfirmModal({
  setModal,
  confirmFunc,
}: RecommendConfirmModalProps) {
  return (
    <div className={MS.modalBackground}>
      <div className={MS.modalBox}>
        <p>모달 내용임</p>
        <button className={BS.WhiteBtn} onClick={() => setModal(false)}>
          취소
        </button>
        <button className={BS.YellowBtn} onClick={confirmFunc}>
          확인
        </button>
      </div>
    </div>
  );
}
