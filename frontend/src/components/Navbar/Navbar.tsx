import { useNavigate } from 'react-router-dom';
import S from './Navbar.module.scss';
import { useCategoryLocation } from '../../hooks/useCategoryLocation';

export const Navbar = () => {
  const navigate = useNavigate();
  const category = useCategoryLocation();

  return (
    <div className={S.container} style={{  }}>
      <div
        className={S.navMain}
        onClick={() => {
          navigate('/');
        }}>
        <img src="./images/icon.png" alt="메인아이콘" className={S.navLogo} />
        <p className={S.navTitle}>Team-Sync</p>
      </div>
      <button
        className={getBtnClassName(category, 'test')}
        onClick={() => {
          navigate('/test');
        }}>
        테스트페이지 이동
      </button>
      <button
        className={getBtnClassName(category, 'project')}
        onClick={() => {
          navigate('/project');
        }}>
        프로젝트 관리
      </button>
      <button
        className={getBtnClassName(category, 'employee')}
        onClick={() => {
          navigate('/employee');
        }}>
        인원 관리
      </button>
    </div>
  );
};

const getBtnClassName = (category: string, target: string) => {
  return category === target ? S.selectedNavBtn : S.navBtn
}