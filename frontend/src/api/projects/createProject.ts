import axios from 'axios';
import { tokenAxios } from '../tokenAPI';
import { API_URL } from '../../constants/envText';
import { headers } from '../../constants/headers';
import { NavigateFunction } from 'react-router-dom';
import { completeMessage } from '../../constants/completeMessage';
import { ERROR_AT_PROJECT_REGISTER } from 'constants/errorMessage';

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
    alert(ERROR_AT_PROJECT_REGISTER)
  } catch (error) {
    console.error(error);
    if (axios.isAxiosError(error)) {
      return error.response?.data;
    }
  }
};
