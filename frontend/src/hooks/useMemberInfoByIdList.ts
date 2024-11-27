import { useEffect, useState } from 'react';
import { checkIsNoData } from 'utils/checkIsNoData';
import { getEmployeeSpec } from 'api/employee/getEmployeeSpec';
import { getEmployeeBasicInfo } from 'api/employee/getEmployeeBasicInfo';

export const useMemberInfoByIdList = (list: number[]) => {
  const [memberInfoList, setMemberInfoList] = useState<any[]>([]);
  const [memberSpecList, setMemberSpecList] = useState<any[]>([]);
  const [memberAllInfoList, setMemberAllInfoList] = useState<any[]>([]);

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

  // 두 배열 합쳐서 제공
  useEffect(() => {
    const mergedList = memberInfoList.map((item, index) => ({ ...item, ...memberSpecList[index] }));
    setMemberAllInfoList(mergedList);
  }, [memberInfoList, memberSpecList]);

  return { memberSpecList, memberInfoList, memberAllInfoList };
};
