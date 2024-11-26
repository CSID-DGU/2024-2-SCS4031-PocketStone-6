import { useEffect, useState } from 'react';
import { checkIsNoData } from 'utils/checkIsNoData';
import { getEmployeeSpec } from 'api/employee/getEmployeeSpec';
import { getEmployeeBasicInfo } from 'api/employee/getEmployeeBasicInfo';

export const useMemberInfoByIdList = (list: number[]) => {
  const [memberInfoList, setMemberInfoList] = useState<any[]>([]);
  const [memberSpecList, setMemberSpecList] = useState<any[]>([]);

  // 멤버 Spec 리스트 얻기
  useEffect(() => {
    if (checkIsNoData(list)) {
      setMemberSpecList([]);
      return;
    }

    const fetchSpecs = async () => {
      const specList = await Promise.all(list.map((id) => getEmployeeSpec(id)));
      setMemberSpecList(specList);
    };

    fetchSpecs();
  }, [list]);

  // 멤버 BasicInfo 리스트 얻기
  useEffect(() => {
    if (checkIsNoData(list)) {
      setMemberInfoList([]);
      return;
    }

    // 비동기 작업 실행 후 결과를 상태에 반영
    const fetchInfos = async () => {
      const infoList = await Promise.all(list.map((id) => getEmployeeBasicInfo(id)));
      setMemberInfoList(infoList);
    };

    fetchInfos();
  }, [list]);

  return { memberSpecList, memberInfoList };
};
