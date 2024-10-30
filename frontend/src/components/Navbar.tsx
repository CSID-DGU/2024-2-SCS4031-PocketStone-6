import { useNavigate } from 'react-router-dom';
import S from './Navbar.module.css';

export const Navbar = () => {
  const navigate = useNavigate();

  return (
    <div
      className={S.container}
      style={{ display: 'flex', alignItems: 'center' }}
    >
      <h2
        onClick={() => {
          navigate('/');
        }}
      >
        Team-Sync
      </h2>
      <button
        onClick={() => {
          navigate('/test');
        }}
      >
        테스트페이지 이동
      </button>
      <button
        onClick={() => {
          navigate('/project');
        }}
      >
        프로젝트 관리
      </button>
      <button
        onClick={() => {
          navigate('/employee');
        }}
      >
        인원 관리
      </button>
    </div>
  );
};
