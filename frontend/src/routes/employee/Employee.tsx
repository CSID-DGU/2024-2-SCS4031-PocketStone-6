import { useNavigate } from 'react-router-dom';
import ExcelUploader from '../../components/Input/ExcelUploader';
import { useAllEmployeeInfoQuery } from '../../hooks/useAllEmployeeInfoQuery';

export default function Employee() {
  const allEmployInfoQuery = useAllEmployeeInfoQuery();

  return (
    <div>
      <p>Employee</p>
      <ExcelUploader />
      {allEmployInfoQuery.data?.map(({employeeId, staffId, name, departmeent, position}: employeeInfoType) => (
        <EmployBlock
          employeeId={employeeId}
          staffId={staffId}
          name={name}
          departmeent={departmeent}
          position={position}
        />
      ))}
    </div>
  );
}

const EmployBlock = ({employeeId, staffId, name, departmeent, position}: employeeInfoType) => {
  const navigate = useNavigate()

  return (
    <div>
      <span>{employeeId} {staffId} {name} {departmeent} {position}</span> ...... <button>삭제</button>
    </div>
  )
}