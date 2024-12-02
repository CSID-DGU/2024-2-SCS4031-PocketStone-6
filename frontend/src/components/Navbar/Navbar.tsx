import { useNavigate } from 'react-router-dom';
import S from './Navbar.module.scss';
import BtnStyles from 'styles/Buttons.module.scss';
import { useCategoryLocation } from '../../hooks/useCategoryLocation';
import { useLoginInfoQuery } from '../../hooks/useLoginInfoQuery';
import { doLogout } from '../../api/auth/authAPI';
import { refreshPage } from '../../utils/movePage';

export const Navbar = () => {
  const navigate = useNavigate();
  const category = useCategoryLocation();
  const { isLogin } = useLoginInfoQuery();

  return (
    <div className={S.container}>
      <div className={S.leftContainer}>
        <div
          className={S.navMain}
          onClick={() => {
            navigate('/');
          }}>
          <img src="/images/icon.png" alt="메인아이콘" className={S.navLogo} />
          <p className={S.navTitle}>Team-Sync</p>
        </div>
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
        <button
          className={S.hiddenNavBtn}
          onClick={() => {
            navigate('/test');
          }}>
          테스트페이지 이동
        </button>
      </div>
      <div className={S.rightContainer}>
        {isLogin ? (
          <button
            className={BtnStyles.YellowBtn}
            onClick={() => {
              doLogout();
              refreshPage(navigate);
            }}>
            로그아웃
          </button>
        ) : (
          <button
            className={BtnStyles.YellowBtn}
            onClick={() => {
              navigate('/login');
            }}>
            로그인
          </button>
        )}
      </div>
    </div>
  );
};

const getBtnClassName = (category: string, target: string) => {
  return category === target ? S.selectedNavBtn : S.navBtn;
};
