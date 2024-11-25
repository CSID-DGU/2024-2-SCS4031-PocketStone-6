import { UseQueryResult } from '@tanstack/react-query';
import { deleteAllProjectMembers } from 'api/projects/deleteAllProjectMembers';
import { useMemberList } from 'hooks/useMemberList';
import { useProjectMemberQuery } from 'hooks/useProjectMemberQuery';
import { useNavigate, useParams } from 'react-router-dom';
import { BS, CS, MS } from 'styles';
import { checkIsNoData } from 'utils/checkIsNoData';

export default function ProjectMember() {
  const { id } = useParams();
  const memberQuery = useProjectMemberQuery(Number(id));
  const navigate = useNavigate();

  return (
    <div className={MS.container}>
      <div className={MS.content}>
        <div className={`${MS.contentTitle} ${MS.displayFlex}`}>
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
          <MemberContent memberQuery={memberQuery} />
        </div>
      </div>
    </div>
  );
}

const MemberContent = ({ memberQuery }: { memberQuery: UseQueryResult }) => {
  return (
    <>
      <div className={CS.contentTitle}>
        <div className={MS.displayFlex}>
          <div className={`${CS.category} ${MS.flexOne}`}>사원번호</div>
          <div className={`${CS.category} ${MS.flexOne}`}>관리번호</div>
          <div className={`${CS.category} ${MS.flexTwo}`}>이름</div>
          <div className={`${CS.category} ${MS.flexTwo}`}>부서</div>
          <div className={`${CS.category} ${MS.flexOne}`}>직책</div>
        </div>
      </div>
      <div className={CS.contentBox}>
        {checkIsNoData(memberQuery.data) ? <NoMemberList /> : <MemberList />}
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

const MemberList = () => {
  const { id } = useParams();
  const { memberIdList, memberSpecList, memberInfoList } = useMemberList(Number(id));

  return (
    <div>
      <p>{JSON.stringify(memberIdList)}</p>
      <p>{JSON.stringify(memberSpecList)}</p>
      <p>{JSON.stringify(memberInfoList)}</p>
    </div>
  );
};
