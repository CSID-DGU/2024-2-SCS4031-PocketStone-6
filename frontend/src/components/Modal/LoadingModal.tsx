import { MS, TS } from 'styles';

export default function LoadingModal() {
  return (
    <div className={MS.modalBackground}>
      <div className={MS.loadingBox}>
        <img src={`/images/loading.gif`} alt='로딩 이미지' />
        <p className={TS.title}>로딩 중...</p>
      </div>
    </div>
  );
}
