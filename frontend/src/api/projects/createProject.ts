import axios from 'axios';
import { tokenAxios } from '../tokenAPI';
import { API_URL } from '../../constants/envText';
import { headers } from '../../constants/headers';
import { NavigateFunction } from 'react-router-dom';
import { completeMessage } from '../../constants/completeMessage';

export const createProject = async (
  projectName: string,
  startDate: string,
  mvpDate: string,
  navigate: NavigateFunction
) => {
  const content: projectInfoType = {
    id: 0,
    projectName,
    startDate,
    mvpDate,
  };

  try {
    const response = await tokenAxios.post(
      `${API_URL}/api/projects/project`,
      content,
      {
        headers: headers,
      }
    );
    if (response.data) {
        alert(completeMessage.CREATE_PROJECT);
        navigate('/project');
        return;
    }
    alert('프로젝트 등록을 하지 못했어요. 관리자에게 문의바랍니다.')
  } catch (error) {
    console.error(error);
    if (axios.isAxiosError(error)) {
      return error.response?.data;
    }
  }
};
