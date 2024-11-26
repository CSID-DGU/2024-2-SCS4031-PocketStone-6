import { UseQueryResult } from '@tanstack/react-query';
import { deleteAllProjectMembers } from 'api/projects/deleteAllProjectMembers';
import EmployeeSpecModal from 'components/Modal/EmployeeSpecModal';
import { useAllEmployeeInfoQuery } from 'hooks/useAllEmployeeInfoQuery';
import { useMemberList } from 'hooks/useMemberList';
import { useProjectMemberQuery } from 'hooks/useProjectMemberQuery';
import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { BS, CS, MS } from 'styles';
import S from './ProjectMember.module.scss';
import { checkIsNoData } from 'utils/checkIsNoData';

export default function ProjectMember() {
  const { id } = useParams();
  const memberQuery = useProjectMemberQuery(Number(id));
  const navigate = useNavigate();
  const [showModal, setShowModal] = useState(false);
  const [currentId, setCurrentId] = useState(1);
  const { memberInfoList } = useMemberList(Number(id));
  const [selectedMemberList, setSelectedMemberList] = useState<number[]>([]);

  useEffect(() => {
    setSelectedMemberList(memberInfoList);
  }, [memberInfoList]);

  return (
    <div className={MS.container}>
      {showModal ? <EmployeeSpecModal id={currentId} setShowModal={setShowModal} /> : null}
      <div className={MS.content}>
        <div className={`${MS.contentTitle} ${S.contentTitle}`}>
          <p>인원 수정</p>
          {checkIsNoData(memberQuery.data) ? null : (
            <button
              className={BS.YellowBtn}
              onClick={() => {
                deleteAllProjectMembers(Number(id), navigate);
              }}>
              전체 인원 삭제
            </button>
          )}
        </div>

        <div className={MS.contentBox}>
          {/* 현재 인원 */}
          <p className={S.smallTitle}>현재 인원</p>
          <EmployeeContent setCurrentId={setCurrentId} setShowModal={setShowModal} />
          <p className={S.downArrow}>👇</p>
          {/* 프로젝트 인원 */}
          <p className={S.smallTitle}>프로젝트 인원</p>
          <MemberContent
            memberQuery={memberQuery}
            setCurrentId={setCurrentId}
            setShowModal={setShowModal}
          />
          {/* 버튼부 */}
          <div className={`${MS.displayFlex} ${MS.flexRight} ${MS.Mt10}`}>
            <button className={`${BS.WhiteBtn} ${MS.Mr10}`}>인원 추천</button>
            <button className={BS.YellowBtn}>수정사항 저장</button>
          </div>
        </div>
      </div>
    </div>
  );
}

const EmployeeContent = ({
  setCurrentId,
  setShowModal,
}: {
  setCurrentId: React.Dispatch<React.SetStateAction<number>>;
  setShowModal: React.Dispatch<React.SetStateAction<boolean>>;
}) => {
  const allEmployInfoQuery = useAllEmployeeInfoQuery();
  return (
    <>
      <div className={CS.contentTitle}>
        <div className={MS.displayFlex}>
          <div className={`${CS.category} ${MS.flexOne}`}>관리번호</div>
          <div className={`${CS.category} ${MS.flexTwo}`}>이름</div>
          <div className={`${CS.category} ${MS.flexTwo}`}>부서</div>
          <div className={`${CS.category} ${MS.flexOne}`}>직책</div>
          <div className={`${CS.category} ${MS.flexOne}`}></div>
        </div>
      </div>
      <div className={CS.contentBox}>
        {checkIsNoData(allEmployInfoQuery.data) ? (
          <NoEmployeeList />
        ) : (
          <EmployeeList
            allEmployInfoQuery={allEmployInfoQuery}
            setCurrentId={setCurrentId}
            setShowModal={setShowModal}
          />
        )}
      </div>
    </>
  );
};

const NoEmployeeList = () => {
  return (
    <div className={CS.notice}>
      <p>사원 정보가 없어요.</p>
      <p>사원정보 등록을 통해 정보를 등록해보세요!</p>
    </div>
  );
};

const EmployeeList = ({
  allEmployInfoQuery,
  setCurrentId,
  setShowModal,
}: {
  allEmployInfoQuery: UseQueryResult<any>;
  setCurrentId: React.Dispatch<React.SetStateAction<number>>;
  setShowModal: React.Dispatch<React.SetStateAction<boolean>>;
}) => {
  return (
    <>
      {allEmployInfoQuery.data?.map(
        ({ employeeId, staffId, name, departmeent, position }: departmeentInfoType, i: number) => (
          <EmployeeBlock
            key={i}
            employeeId={employeeId}
            staffId={staffId}
            name={name}
            department={departmeent}
            position={position}
            setCurrentId={setCurrentId}
            setShowModal={setShowModal}
          />
        )
      )}
    </>
  );
};

interface MemberBlockProps {
  employeeId: number;
  staffId: string;
  name: string;
  department: string;
  position: string;
  setCurrentId: React.Dispatch<React.SetStateAction<number>>;
  setShowModal: React.Dispatch<React.SetStateAction<boolean>>;
}

const EmployeeBlock = ({
  employeeId,
  staffId,
  name,
  department,
  position,
  setCurrentId,
  setShowModal,
}: MemberBlockProps) => {
  const navigate = useNavigate();

  return (
    <div className={CS.container}>
      <div className={CS.card}>
        <div
          className={CS.canClickPart}
          onClick={() => {
            setCurrentId(employeeId);
            setShowModal(true);
          }}>
          <div className={`${CS.category} ${MS.flexOne}`}>{staffId}</div>
          <div className={`${CS.category} ${MS.flexTwo}`}>{name}</div>
          <div className={`${CS.category} ${MS.flexTwo}`}>{department}</div>
          <div className={`${CS.category} ${MS.flexOne}`}>{position}</div>
        </div>
        <div className={CS.noClickPart}>
          <div className={`${CS.category} ${MS.flexOne}`}>
            <button className={BS.YellowBtn} onClick={() => {}}>
              추가
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

const MemberContent = ({
  memberQuery,
  setCurrentId,
  setShowModal,
}: {
  memberQuery: UseQueryResult;
  setCurrentId: React.Dispatch<React.SetStateAction<number>>;
  setShowModal: React.Dispatch<React.SetStateAction<boolean>>;
}) => {
  return (
    <>
      <div className={CS.contentTitle}>
        <div className={MS.displayFlex}>
          <div className={`${CS.category} ${MS.flexOne}`}>관리번호</div>
          <div className={`${CS.category} ${MS.flexTwo}`}>이름</div>
          <div className={`${CS.category} ${MS.flexTwo}`}>부서</div>
          <div className={`${CS.category} ${MS.flexOne}`}>직책</div>
          <div className={`${CS.category} ${MS.flexOne}`}></div>
        </div>
      </div>
      <div className={CS.contentBox}>
        {checkIsNoData(memberQuery.data) ? (
          <NoMemberList />
        ) : (
          <MemberList setCurrentId={setCurrentId} setShowModal={setShowModal} />
        )}
      </div>
    </>
  );
};

const NoMemberList = () => {
  return (
    <div className={CS.notice}>
      <p>멤버 정보가 없어요.</p>
      <p>사원정보에서 멤버를 새로 등록해보세요!</p>
    </div>
  );
};

const MemberList = ({
  setCurrentId,
  setShowModal,
}: {
  setCurrentId: React.Dispatch<React.SetStateAction<number>>;
  setShowModal: React.Dispatch<React.SetStateAction<boolean>>;
}) => {
  const { id } = useParams();
  const { memberInfoList } = useMemberList(Number(id));

  return (
    <>
      {memberInfoList.map(
        ({ employeeId, staffId, name, department, position }: employeeInfoType, i: number) => {
          return (
            <MemberBlock
              key={i}
              employeeId={employeeId}
              staffId={staffId}
              name={name}
              department={department}
              position={position}
              setCurrentId={setCurrentId}
              setShowModal={setShowModal}
            />
          );
        }
      )}
    </>
  );
};

interface MemberBlockProps {
  employeeId: number;
  staffId: string;
  name: string;
  department: string;
  position: string;
  setCurrentId: React.Dispatch<React.SetStateAction<number>>;
  setShowModal: React.Dispatch<React.SetStateAction<boolean>>;
}

const MemberBlock = ({
  employeeId,
  staffId,
  name,
  department,
  position,
  setCurrentId,
  setShowModal,
}: MemberBlockProps) => {
  return (
    <div className={CS.container}>
      <div className={CS.card}>
        <div
          className={CS.canClickPart}
          onClick={() => {
            setCurrentId(employeeId);
            setShowModal(true);
          }}>
          <div className={`${CS.category} ${MS.flexOne}`}>{staffId}</div>
          <div className={`${CS.category} ${MS.flexTwo}`}>{name}</div>
          <div className={`${CS.category} ${MS.flexTwo}`}>{department}</div>
          <div className={`${CS.category} ${MS.flexOne}`}>{position}</div>
        </div>
        <div className={CS.noClickPart}>
          <div className={`${CS.category} ${MS.flexOne}`}>
            <button className={BS.YellowBtn} onClick={() => {}}>
              삭제
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
