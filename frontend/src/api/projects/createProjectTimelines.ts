import axios from 'axios';
import { tokenAxios } from '../tokenAPI';
import { API_URL } from '../../constants/envText';
import { headers } from '../../constants/headers';
import { NavigateFunction } from 'react-router-dom';
import { completeMessage } from '../../constants/completeMessage';
import { ERROR_AT_CHARTER_REGISTER } from 'constants/errorMessage';
import { refreshPage } from 'utils/movePage';

export const createProjectTimelines = async (projectId: number, sprintOrder: number, navigate: NavigateFunction) => {
  const content = [
    {
      sprintOrder: sprintOrder,
      sprintContent: '스프린트 내용',
      sprintDurationWeek: 1,
    },
  ];

  try {
    const response = await tokenAxios.post(
      `${API_URL}/api/projects/timelines/${projectId}`,
      content,
      {
        headers: headers,
      }
    );
    if (response.data) {
      alert(completeMessage.CREATE_CHARTER);
      refreshPage(navigate);
      return;
    }
  } catch (error) {
    console.error(error);
    if (axios.isAxiosError(error)) {
      alert(`${error.response?.data?.message}`);
      return;
    }
    alert(`${ERROR_AT_CHARTER_REGISTER}`);
  }
};
