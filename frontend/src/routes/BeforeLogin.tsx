import { BS, MS } from 'styles';
import S from './BeforeLogin.module.scss';
import { useNavigate } from 'react-router-dom';

export default function BeforeLogin() {
  const navigate = useNavigate();

  return (
    <div className={S.container}>
      <img src="/images/icon.png" alt="로고" />
      <p className={S.title}>Team-Sync</p>
      <p className={S.description}>사내 데이터를 이용한 프로젝트 팀빌딩 시스템</p>
      <button className={`${BS.YellowBtn} ${S.btn} ${MS.Mb20}`} onClick={()=>{navigate('/login')}}>
        로그인
      </button>
      <button className={`${BS.WhiteBtn} ${S.btn}`} onClick={()=>{navigate('/register')}}>회원가입</button>
    </div>
  );
}
