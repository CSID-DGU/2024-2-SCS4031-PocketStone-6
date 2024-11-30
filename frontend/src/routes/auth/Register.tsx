import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { checkID, doRegister } from '../../api/auth/authAPI';
import S from './Register.module.scss';
import { BS, IS } from 'styles';

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
    <div className={S.joinContent}>
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
        아이디 중복 확인
      </button>
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
        onClick={async () => {
          const response = await doRegister(
            id,
            `${emailID}@${emailDomain}`,
            password,
            companyName,
            navigate
          );
          console.log(response);
        }}>
        회원가입
      </button>
    </div>
  );
};
