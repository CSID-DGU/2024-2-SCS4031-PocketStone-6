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
import { IoMdDownload } from 'react-icons/io';
import { getExcelTemplate } from 'api/download/getExcelTemplate';
import { FaTrash } from 'react-icons/fa';

export default function Employee() {
  const navigate = useNavigate();

  return (
    <div className={MS.container}>
      <div className={MS.content}>
        <div className={`${MS.contentTitle} ${MS.displayFlex} ${MS.flexSpace}`}>
          <p>사원정보 엑셀 등록/삭제</p>
          <div className={S.btnsDiv}>
            <button
              className={`${BS.WhiteBtn} ${S.iconBtn}`}
              onClick={() => {
                getExcelTemplate('employee');
              }}>
              <IoMdDownload /> 사원 정보 양식
            </button>
          </div>
        </div>
        <div className={MS.contentBox}>
          <ExcelUploader />
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

export const EmployeeContent = () => {
  const allEmployInfoQuery = useAllEmployeeInfoQuery();

  return (
    <>
      <div className={S.contentTitle}>
        <div style={{ display: 'flex' }}>
          <div className={`${S.category} ${S.flexOne}`}>관리번호</div>
          <div className={`${S.category} ${S.flexOne}`}>이름</div>
          <div className={`${S.category} ${S.flexOne}`}>부서</div>
          <div className={`${S.category} ${S.flexOne}`}>직책</div>
          <div className={`${S.category} ${S.flexTwo}`}>역할</div>
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
    ({ employeeId, staffId, name, department, position, role }: employeeInfoType, i: number) => (
      <EmployeeBlock
        key={i}
        employeeId={employeeId}
        staffId={staffId}
        name={name}
        department={department}
        position={position}
        role={role}
      />
    )
  );
};

const EmployeeBlock = ({
  employeeId,
  staffId,
  name,
  department,
  position,
  role,
}: employeeInfoType) => {
  const navigate = useNavigate();

  return (
    <div className={EmployBlockStyle.container}>
      <div className={EmployBlockStyle.card}>
        <div
          className={EmployBlockStyle.canClickPart}
          onClick={() => {
            navigate(`/employee/${employeeId}`);
          }}>
          <div className={`${S.category} ${S.flexOne}`}>{staffId}</div>
          <div className={`${S.category} ${S.flexOne}`}>{name}</div>
          <div className={`${S.category} ${S.flexOne}`}>{department}</div>
          <div className={`${S.category} ${S.flexOne}`}>{position}</div>
          <div className={`${S.category} ${S.flexTwo}`}>{role}</div>
        </div>
        <div className={EmployBlockStyle.noClickPart}>
          <div className={`${S.category} ${S.flexOne}`}>
            <button
              className={`${BS.removeBtn} ${MS.width100}`}
              onClick={async () => {
                await deleteOneEmployee(employeeId);
                refreshPage(navigate);
              }}>
              <FaTrash />
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
