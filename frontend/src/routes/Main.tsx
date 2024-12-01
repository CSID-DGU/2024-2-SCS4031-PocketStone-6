import { useNavigate } from 'react-router-dom';
import { BS, MS } from 'styles';
import { ProjectContent } from './project/Project';
import { EmployeeContent } from './employee/Employee';
import { deleteAllEmployInfo } from 'api/employee/deleteAllEmployInfo';

export default function Main() {
  const navigate = useNavigate();

  return (
    <div className={MS.container}>
      <div className={MS.content}>
        <div className={`${MS.contentTitle} ${MS.displayFlex} ${MS.flexSpace}`}>
          <p>프로젝트 목록</p>
          <button
            className={BS.YellowBtn}
            onClick={() => {
              navigate('/project/new');
            }}>
            ➕ 새로운 프로젝트
          </button>
        </div>
        <div className={MS.contentBox}>
          <ProjectContent />
        </div>
      </div>

      <div className={MS.content}>
        <div className={`${MS.contentTitle} ${MS.displayFlex} ${MS.flexSpace}`}>
          <p>사원 목록</p>
          <button className={BS.YellowBtn} onClick={() => deleteAllEmployInfo(navigate)}>
            사원정보 삭제
          </button>
        </div>
        <div className={MS.contentBox}>
          <div>
            <EmployeeContent />
          </div>
        </div>
      </div>
    </div>
  );
}
