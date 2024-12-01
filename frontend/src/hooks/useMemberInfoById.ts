import { useEffect, useState } from 'react';
import { getEmployeeSpec } from 'api/employee/getEmployeeSpec';
import { getEmployeeBasicInfo } from 'api/employee/getEmployeeBasicInfo';

export const useMemberInfoById = (id: number) => {
  const [memberInfo, setMemberInfo] = useState<{}>({});
  const [memberSpec, setMemberSpec] = useState<{}>({});
  const [memberAllInfo, setMemberAllInfo] = useState<any>({});

  // 멤버 Spec 얻기
  useEffect(() => {
    const fetchSpec = async (id: number) => {
      const specInfo = await getEmployeeSpec(id);
      setMemberSpec(specInfo);
    };

    fetchSpec(id);
  }, [id]);

  // 멤버 BasicInfo 얻기
  useEffect(() => {
    const fetchInfos = async (id: number) => {
      const infoList = await getEmployeeBasicInfo(id);
      setMemberInfo(infoList);
    };

    fetchInfos(id);
  }, [id]);

  // 두 배열 합쳐서 제공
  useEffect(() => {
    const mergedInfo = { ...memberSpec, ...memberInfo };
    setMemberAllInfo(mergedInfo);
  }, [memberSpec, memberInfo]);

  return { memberSpec, memberInfo, memberAllInfo };
};
