import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { checkID, doRegister } from '../../api/auth/authAPI';
import S from './Register.module.scss';
import { BS, IS } from 'styles';
import { NOT_CORRECT_PASSWORD } from 'constants/errorMessage';

export default function Register() {
  return (
    <div className={S.container}>
      <div className={S.content}>
        <JoinContent />
      </div>
    </div>
  );
}

const JoinContent = () => {
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [emailID, setEmailID] = useState('');
  const [emailDomain, setEmailDomain] = useState('');
  const [companyName, setCompanyName] = useState('');

  const navigate = useNavigate();

  return (
    <>
      <div className={S.joinIcon}>
        <img src="./images/icon.png" alt="메인아이콘" />
        <p>Team-Sync</p>
      </div>
      <div className={S.joinContent}>
        <div className={S.joinID}>
          <input
            className={IS.textInput}
            type="text"
            placeholder="ID"
            onChange={(e) => {
              setId(e.target.value);
            }}></input>
          <button
            className={BS.WhiteBtn}
            onClick={async () => {
              console.log(id);
              const response = await checkID(id);
              console.log(response);
            }}>
            ID 중복 확인
          </button>
        </div>
        <input
          className={IS.textInput}
          type="password"
          placeholder="비밀번호 입력"
          onChange={(e) => {
            setPassword(e.target.value);
          }}></input>
        <input
          className={IS.textInput}
          type="password"
          placeholder="비밀번호 확인"
          onChange={(e) => {
            setPasswordConfirm(e.target.value);
          }}></input>
        <input
          className={IS.textInput}
          type="text"
          placeholder="이메일 ID"
          onChange={(e) => {
            setEmailID(e.target.value);
          }}></input>
        <input
          className={IS.textInput}
          type="text"
          placeholder="이메일 주소"
          onChange={(e) => {
            setEmailDomain(e.target.value);
          }}></input>
        <input
          className={IS.textInput}
          type="text"
          placeholder="회사명"
          onChange={(e) => {
            setCompanyName(e.target.value);
          }}></input>
        <button
          className={BS.YellowBtn}
          onClick={() => {
            if (password !== passwordConfirm) {
              alert(NOT_CORRECT_PASSWORD);
              return;
            }
            doRegister(id, `${emailID}@${emailDomain}`, password, companyName, navigate);
          }}>
          회원가입
        </button>
        <button
          className={BS.WhiteBtn}
          onClick={() => {
            navigate('/login');
          }}>
          로그인하러 가기
        </button>
      </div>
    </>
  );
};
