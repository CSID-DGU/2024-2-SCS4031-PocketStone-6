import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { doLogin } from '../../api/auth/authAPI';

export default function Login() {
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  return (
    <div>
      <input
        type="text"
        onChange={(e) => {
          setId(e.target.value);
        }}
      ></input>
      <input
        type="password"
        onChange={(e) => {
          setPassword(e.target.value);
        }}
      ></input>
      <button
        onClick={() => {
          doLogin(id, password, navigate);
        }}
      >
        로그인
      </button>
    </div>
  );
}
