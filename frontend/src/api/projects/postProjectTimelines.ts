import axios from 'axios';
import { tokenAxios } from '../tokenAPI';
import { API_URL } from '../../constants/envText';
import { headers } from '../../constants/headers';
import { NavigateFunction } from 'react-router-dom';
import { completeMessage } from '../../constants/completeMessage';
import { ERROR_AT_CHARTER_REGISTER } from 'constants/errorMessage';

export const postProjectTimelines = async (
  projectId: number,
  content: TimelineData[],
  navigate: NavigateFunction
) => {
  try {
    const response = await tokenAxios.post(
      `${API_URL}/api/projects/timelines/${projectId}`,
      content,
      {
        headers: headers,
      }
    );
    if (response.data) {
      alert(completeMessage.MODIFY_TIMELINES);
      navigate(`/project/${projectId}`);
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
