import { useNavigate } from 'react-router-dom';
import ExcelUploader from '../../components/Input/ExcelUploader';
import { useAllEmployeeInfoQuery } from '../../hooks/useAllEmployeeInfoQuery';
import { deleteOneEmployee } from '../../api/employee/deleteOneEmployee';
import { refreshPage } from '../../utils/movePage';

export default function Employee() {
  const allEmployInfoQuery = useAllEmployeeInfoQuery();

  return (
    <div>
      <h2>Employee</h2>
      <ExcelUploader />
      {allEmployInfoQuery.data?.map(
        ({ employeeId, staffId, name, departmeent, position }: employeeInfoType, i: number) => (
          <EmployBlock
            key={i}
            employeeId={employeeId}
            staffId={staffId}
            name={name}
            departmeent={departmeent}
            position={position}
          />
        )
      )}
    </div>
  );
}

const EmployBlock = ({ employeeId, staffId, name, departmeent, position }: employeeInfoType) => {
  const navigate = useNavigate();

  return (
    <div>
      <span
        onClick={() => {
          navigate(`/employee/${employeeId}`);
        }}>
        {employeeId} {staffId} {name} {departmeent} {position}
      </span>
      ......{' '}
      <button
        onClick={async () => {
          await deleteOneEmployee(employeeId);
          refreshPage(navigate);
        }}>
        삭제
      </button>
    </div>
  );
};
