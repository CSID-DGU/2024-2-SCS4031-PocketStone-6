import { useEffect, useState } from 'react';
import { useProjectMemberQuery } from './useProjectMemberQuery';
import { checkIsNoData } from 'utils/checkIsNoData';
import { getEmployeeSpec } from 'api/employee/getEmployeeSpec';
import { getEmployeeBasicInfo } from 'api/employee/getEmployeeBasicInfo';

export const useMemberList = (id: number) => {
  const [memberIdList, setMemberIdList] = useState<number[]>([]);
  const [memberSpecList, setMemberSpecList] = useState<any[]>([]);
  const [memberInfoList, setMemberInfoList] = useState<any[]>([]);
  const memberQuery = useProjectMemberQuery(id);

  // 멤버 id 리스트 업데이트
  useEffect(() => {
    const idList =
      memberQuery.data?.map(({ employeeId }: { employeeId: number }) => employeeId) || [];
    setMemberIdList(idList);
  }, [memberQuery.data]);

  // 멤버 Spec 리스트 얻기
  useEffect(() => {
    if (checkIsNoData(memberIdList)) {
      setMemberSpecList([]);
      return;
    }

    const fetchSpecs = async () => {
      const specList = await Promise.all(memberIdList.map((id) => getEmployeeSpec(id)));
      setMemberSpecList(specList);
    };

    fetchSpecs();
  }, [memberIdList]);

  // 멤버 BasicInfo 리스트 얻기
  useEffect(() => {
    if (checkIsNoData(memberIdList)) {
      setMemberInfoList([]);
      return;
    }

    // 비동기 작업 실행 후 결과를 상태에 반영
    const fetchInfos = async () => {
      const infoList = await Promise.all(memberIdList.map((id) => getEmployeeBasicInfo(id)));
      setMemberInfoList(infoList);
    };

    fetchInfos();
  }, [memberIdList]);

  return { memberIdList, memberSpecList, memberInfoList };
};
