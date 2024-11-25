import { useNavigate } from 'react-router-dom';
import ExcelUploader from '../../components/Input/ExcelUploader';
import { useAllEmployeeInfoQuery } from '../../hooks/useAllEmployeeInfoQuery';
import { deleteOneEmployee } from '../../api/employee/deleteOneEmployee';
import { refreshPage } from '../../utils/movePage';
import { deleteAllEmployInfo } from '../../api/employee/deleteAllEmployInfo';
import S from './Employee.module.scss';
import EmployBlockStyle from './EmployeeBlock.module.scss';
import { UseQueryResult } from '@tanstack/react-query';
import { BS, MS } from 'styles';
import { checkIsNoData } from 'utils/checkIsNoData';

export default function Employee() {
  const navigate = useNavigate();

  return (
    <div className={MS.container}>
      <div className={MS.content}>
        <div className={MS.contentTitle}>
          <p>사원정보 엑셀 등록/삭제</p>
        </div>
        <div className={MS.contentBox}>
          <ExcelUploader />
          <button onClick={() => deleteAllEmployInfo(navigate)}>사원정보 삭제</button>
        </div>
      </div>

      <div className={MS.content}>
        <div className={MS.contentTitle}>
          <p>사원 목록</p>
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

const EmployeeContent = () => {
  const allEmployInfoQuery = useAllEmployeeInfoQuery();

  return (
    <>
      <div className={S.contentTitle}>
        <div style={{ display: 'flex' }}>
          <div className={`${S.category} ${S.flexOne}`}>사원번호</div>
          <div className={`${S.category} ${S.flexOne}`}>관리번호</div>
          <div className={`${S.category} ${S.flexTwo}`}>이름</div>
          <div className={`${S.category} ${S.flexTwo}`}>부서</div>
          <div className={`${S.category} ${S.flexOne}`}>직책</div>
          <div className={`${S.category} ${S.flexOne}`}></div>
        </div>
      </div>
      <div className={S.contentBox}>
        {checkIsNoData(allEmployInfoQuery.data) ? (
          <NoEmployeeData />
        ) : (
          <EmployeeData allEmployInfoQuery={allEmployInfoQuery} />
        )}
      </div>
    </>
  );
};

const NoEmployeeData = () => {
  return (
    <div className={S.notice}>
      <p>사원 정보가 없어요.</p>
      <p>사원정보 등록을 통해 정보를 등록해보세요!</p>
    </div>
  );
};

const EmployeeData = ({ allEmployInfoQuery }: { allEmployInfoQuery: UseQueryResult<any> }) => {
  return allEmployInfoQuery.data?.map(
    ({ employeeId, staffId, name, departmeent, position }: employeeInfoType, i: number) => (
      <EmployeeBlock
        key={i}
        employeeId={employeeId}
        staffId={staffId}
        name={name}
        departmeent={departmeent}
        position={position}
      />
    )
  );
};

const EmployeeBlock = ({ employeeId, staffId, name, departmeent, position }: employeeInfoType) => {
  const navigate = useNavigate();

  return (
    <div className={EmployBlockStyle.container}>
      <div className={EmployBlockStyle.card}>
        <div
          className={EmployBlockStyle.canClickPart}
          onClick={() => {
            navigate(`/employee/${employeeId}`);
          }}>
          <div className={`${S.category} ${S.flexOne}`}>{employeeId}</div>
          <div className={`${S.category} ${S.flexOne}`}>{staffId}</div>
          <div className={`${S.category} ${S.flexTwo}`}>{name}</div>
          <div className={`${S.category} ${S.flexTwo}`}>{departmeent}</div>
          <div className={`${S.category} ${S.flexOne}`}>{position}</div>
        </div>
        <div className={EmployBlockStyle.noClickPart}>
          <div className={`${S.category} ${S.flexOne}`}>
            <button
              className={BS.YellowBtn}
              onClick={async () => {
                await deleteOneEmployee(employeeId);
                refreshPage(navigate);
              }}>
              삭제
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
