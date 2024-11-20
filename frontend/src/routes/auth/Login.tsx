import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { doLogin } from '../../api/auth/authAPI';
import S from './Login.module.css';
import IS from 'styles/Inputs.module.scss';
import BS from 'styles/Buttons.module.scss';

export default function Login() {
  return (
    <div className={S.container}>
      <div className={S.content}>
        <div className={S.loginBox}>
          <LoginContent />
        </div>
        <div className={S.joinBox}>
          <JoinContent />
        </div>
      </div>
    </div>
  );
}

const LoginContent = () => {
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  return (
    <div className={S.loginContent}>
      <img src="./images/icon.png" alt="메인아이콘" />
      <p className={S.loginTitle}>Team-Sync</p>
      <p>로그인 후 저희의 서비스를 이용해보세요!</p>
      <input
        className={IS.textInput}
        placeholder="ID"
        type="text"
        onChange={(e) => {
          setId(e.target.value);
        }}></input>
      <input
        className={IS.textInput}
        placeholder="PASSWORD"
        type="password"
        onChange={(e) => {
          setPassword(e.target.value);
        }}></input>
      <button
        className={BS.YellowBtn}
        onClick={() => {
          doLogin(id, password, navigate);
        }}>
        로그인
      </button>
    </div>
  );
};

const JoinContent = () => {
  const navigate = useNavigate();

  return (
    <div className={S.joinContent}>
      <p className={S.joinTitle}>안녕하세요!</p>
      <p className={S.joinText}>사내 데이터를 이용한 팀 추천 서비스</p>
      <p className={S.joinText}>Team-Sync에 처음 오셨나요?</p>
      <button
      className={BS.WhiteBtn}
        onClick={() => {
          navigate('/register');
        }}>
        회원가입
      </button>
    </div>
  );
};
