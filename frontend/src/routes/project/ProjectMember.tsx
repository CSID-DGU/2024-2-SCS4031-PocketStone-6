import { UseQueryResult } from '@tanstack/react-query';
import { deleteAllProjectMembers } from 'api/member/deleteAllProjectMembers';
import EmployeeSpecModal from 'components/Modal/EmployeeSpecModal';
import { useAllEmployeeInfoQuery } from 'hooks/useAllEmployeeInfoQuery';
import { useMemberList } from 'hooks/useMemberList';
import { useProjectMemberQuery } from 'hooks/useProjectMemberQuery';
import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { BS, CS, MS } from 'styles';
import S from './ProjectMember.module.scss';
import { checkIsNoData } from 'utils/checkIsNoData';
import { useScrollBlock } from 'hooks/useScrollBlock';
import { useMemberInfoByIdList } from 'hooks/useMemberInfoByIdList';
import { addElementAtList, deleteElementAtList } from 'utils/parseList';
import { addProjectMembers } from 'api/member/addProjectMembers';
import { FaPlus, FaTrash } from 'react-icons/fa';
import LoadingModal from 'components/Modal/LoadingModal';
import { getRecommendation } from 'api/member/getRecommendation';
import RecommendationModal from 'components/Modal/RecommendationModal';

export default function ProjectMember() {
  const { id } = useParams();
  const memberQuery = useProjectMemberQuery(Number(id));
  const navigate = useNavigate();
  const [showModal, setShowModal] = useState(false);
  const [currentId, setCurrentId] = useState(1);
  const { memberIdList } = useMemberList(Number(id));
  const [selectedMemberList, setSelectedMemberList] = useState<number[]>([]);
  const [loading, setLoading] = useState(false);
  const [recommendationData, setRecommendationData] = useState({});

  const handleRecommendationClick = async () => {
    setLoading(true);
    try {
      const data = await getRecommendation(Number(id));
      setRecommendationData(data);
    } catch (error) {
      console.error('추천 요청 실패:', error);
    } finally {
      setLoading(false);
    }
  };

  useScrollBlock(showModal || loading || !checkIsNoData(recommendationData));

  useEffect(() => {
    setSelectedMemberList(memberIdList);
  }, [memberIdList]);

  return (
    <div className={MS.container}>
      {loading && <LoadingModal />}
      {!checkIsNoData(recommendationData) && (
        <RecommendationModal data={recommendationData} setData={setRecommendationData} />
      )}
      {showModal && <EmployeeSpecModal id={currentId} setShowModal={setShowModal} />}
      <div className={MS.content}>
        <div className={`${MS.contentTitle} ${S.contentTitle}`}>
          <p>인원 수정</p>
          {checkIsNoData(memberQuery.data) ? null : (
            <button
              className={BS.YellowBtn}
              onClick={() => {
                deleteAllProjectMembers(Number(id), navigate);
              }}>
              전체 인원 삭제 및 저장
            </button>
          )}
        </div>

        <div className={MS.contentBox}>
          {/* 현재 인원 */}
          <p className={S.smallTitle}>현재 인원</p>
          <EmployeeContent
            list={selectedMemberList}
            setList={setSelectedMemberList}
            setCurrentId={setCurrentId}
            setShowModal={setShowModal}
          />
          <p className={S.downArrow}>👇</p>
          {/* 프로젝트 인원 */}
          <p className={S.smallTitle}>프로젝트 인원</p>
          <MemberContent
            list={selectedMemberList}
            setList={setSelectedMemberList}
            setCurrentId={setCurrentId}
            setShowModal={setShowModal}
          />
          {/* 버튼부 */}
          <div className={`${MS.displayFlex} ${MS.flexRight} ${MS.Mt10}`}>
            <button className={`${BS.WhiteBtn} ${MS.Mr10}`} onClick={handleRecommendationClick}>
              인원 추천
            </button>
            <button
              className={BS.YellowBtn}
              onClick={() => {
                addProjectMembers(Number(id), selectedMemberList, navigate);
              }}>
              수정사항 저장
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

const EmployeeContent = ({
  list,
  setList,
  setCurrentId,
  setShowModal,
}: {
  list: number[];
  setList: React.Dispatch<React.SetStateAction<number[]>>;
  setCurrentId: React.Dispatch<React.SetStateAction<number>>;
  setShowModal: React.Dispatch<React.SetStateAction<boolean>>;
}) => {
  const allEmployInfoQuery = useAllEmployeeInfoQuery();
  return (
    <>
      <div className={CS.contentTitle}>
        <div className={MS.displayFlex}>
          <div className={`${CS.category} ${MS.flexOne}`}>관리번호</div>
          <div className={`${CS.category} ${MS.flexOne}`}>이름</div>
          <div className={`${CS.category} ${MS.flexOne}`}>부서</div>
          <div className={`${CS.category} ${MS.flexOne}`}>직책</div>
          <div className={`${CS.category} ${MS.flexTwo}`}>역할</div>
          <div className={`${CS.category} ${MS.flexOne}`}></div>
        </div>
      </div>
      <div className={CS.contentBox}>
        {checkIsNoData(allEmployInfoQuery.data) ? (
          <NoEmployeeList />
        ) : (
          <EmployeeList
            list={list}
            setList={setList}
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
  list,
  setList,
  allEmployInfoQuery,
  setCurrentId,
  setShowModal,
}: {
  list: number[];
  setList: React.Dispatch<React.SetStateAction<number[]>>;
  allEmployInfoQuery: UseQueryResult<any>;
  setCurrentId: React.Dispatch<React.SetStateAction<number>>;
  setShowModal: React.Dispatch<React.SetStateAction<boolean>>;
}) => {
  return (
    <>
      {allEmployInfoQuery.data?.map(
        (
          { employeeId, staffId, name, department, position, role }: employeeInfoType,
          i: number
        ) => (
          <EmployeeBlock
            key={i}
            list={list}
            setList={setList}
            employeeId={employeeId}
            staffId={staffId}
            name={name}
            department={department}
            position={position}
            role={role}
            setCurrentId={setCurrentId}
            setShowModal={setShowModal}
          />
        )
      )}
    </>
  );
};

interface EmployeeBlockProps {
  list: number[];
  setList: React.Dispatch<React.SetStateAction<number[]>>;
  employeeId: number;
  staffId: string;
  name: string;
  department: string;
  position: string;
  role: string;
  setCurrentId: React.Dispatch<React.SetStateAction<number>>;
  setShowModal: React.Dispatch<React.SetStateAction<boolean>>;
}

const EmployeeBlock = ({
  list,
  setList,
  employeeId,
  staffId,
  name,
  department,
  position,
  role,
  setCurrentId,
  setShowModal,
}: EmployeeBlockProps) => {
  return (
    <div className={list.includes(employeeId) ? CS.containerSelected : CS.container}>
      <div className={CS.card}>
        <div
          className={list.includes(employeeId) ? CS.canClickPartSelected : CS.canClickPart}
          onClick={() => {
            setCurrentId(employeeId);
            setShowModal(true);
          }}>
          <div className={`${CS.category} ${MS.flexOne}`}>{staffId}</div>
          <div className={`${CS.category} ${MS.flexOne}`}>{name}</div>
          <div className={`${CS.category} ${MS.flexOne}`}>{department}</div>
          <div className={`${CS.category} ${MS.flexOne}`}>{position}</div>
          <div className={`${CS.category} ${MS.flexTwo}`}>{role}</div>
        </div>
        <div className={CS.noClickPart}>
          <div className={`${CS.category} ${MS.flexOne}`}>
            {list.includes(employeeId) ? (
              <button
                className={`${BS.removeBtn} ${MS.width100}`}
                onClick={() => {
                  setList(deleteElementAtList(employeeId, list));
                }}>
                <FaTrash />
              </button>
            ) : (
              <button
                className={`${BS.addBtn} ${MS.width100}`}
                onClick={() => {
                  setList(addElementAtList(employeeId, list));
                }}>
                <FaPlus />
              </button>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

const MemberContent = ({
  list,
  setList,
  setCurrentId,
  setShowModal,
}: {
  list: number[];
  setList: React.Dispatch<React.SetStateAction<number[]>>;
  setCurrentId: React.Dispatch<React.SetStateAction<number>>;
  setShowModal: React.Dispatch<React.SetStateAction<boolean>>;
}) => {
  return (
    <>
      <div className={CS.contentTitle}>
        <div className={MS.displayFlex}>
          <div className={`${CS.category} ${MS.flexOne}`}>관리번호</div>
          <div className={`${CS.category} ${MS.flexOne}`}>이름</div>
          <div className={`${CS.category} ${MS.flexOne}`}>부서</div>
          <div className={`${CS.category} ${MS.flexOne}`}>직책</div>
          <div className={`${CS.category} ${MS.flexTwo}`}>역할</div>
          <div className={`${CS.category} ${MS.flexOne}`}></div>
        </div>
      </div>
      <div className={CS.contentBox}>
        {checkIsNoData(list) ? (
          <NoMemberList />
        ) : (
          <MemberList
            list={list}
            setList={setList}
            setCurrentId={setCurrentId}
            setShowModal={setShowModal}
          />
        )}
      </div>
    </>
  );
};

const NoMemberList = () => {
  return (
    <div className={CS.notice}>
      <p>멤버 정보가 없어요.</p>
      <p>현재 인원에서 멤버를 추가해보세요!</p>
    </div>
  );
};

const MemberList = ({
  list,
  setList,
  setCurrentId,
  setShowModal,
}: {
  list: number[];
  setList: React.Dispatch<React.SetStateAction<number[]>>;
  setCurrentId: React.Dispatch<React.SetStateAction<number>>;
  setShowModal: React.Dispatch<React.SetStateAction<boolean>>;
}) => {
  const { memberAllInfoList } = useMemberInfoByIdList(list);
  return (
    <>
      {memberAllInfoList.map(
        (
          { employeeId, staffId, name, department, position, role }: employeeInfoType,
          i: number
        ) => {
          return (
            <MemberBlock
              key={i}
              list={list}
              setList={setList}
              employeeId={employeeId}
              staffId={staffId}
              name={name}
              department={department}
              position={position}
              role={role}
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
  list: number[];
  setList: React.Dispatch<React.SetStateAction<number[]>>;
  employeeId: number;
  staffId: string;
  name: string;
  department: string;
  position: string;
  role: string;
  setCurrentId: React.Dispatch<React.SetStateAction<number>>;
  setShowModal: React.Dispatch<React.SetStateAction<boolean>>;
}

const MemberBlock = ({
  list,
  setList,
  employeeId,
  staffId,
  name,
  department,
  position,
  role,
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
          <div className={`${CS.category} ${MS.flexOne}`}>{name}</div>
          <div className={`${CS.category} ${MS.flexOne}`}>{department}</div>
          <div className={`${CS.category} ${MS.flexOne}`}>{position}</div>
          <div className={`${CS.category} ${MS.flexTwo}`}>{role}</div>
        </div>
        <div className={CS.noClickPart}>
          <div className={`${CS.category} ${MS.flexOne}`}>
            <button
              className={`${BS.removeBtn} ${MS.width100}`}
              onClick={() => {
                setList(deleteElementAtList(employeeId, list));
              }}>
              <FaTrash />
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
