import axios from 'axios';
import { tokenAxios } from '../tokenAPI';
import { API_URL } from '../../constants/envText';
import { headers } from '../../constants/headers';
import { NavigateFunction } from 'react-router-dom';
import { completeMessage } from '../../constants/completeMessage';
import { ERROR_AT_CHARTER_REGISTER } from 'constants/errorMessage';
import { parseCharterContent } from 'utils/parseCharterContent';

export const putProjectCharter = async (
  projectId: number,
  content: CharterContent,
  navigate: NavigateFunction
) => {
  const parsedCharterContent = parseCharterContent(content);

  try {
    const response = await tokenAxios.put(
      `${API_URL}/api/projects/charter/${projectId}`,
      parsedCharterContent,
      {
        headers: headers,
      }
    );
    if (response.data) {
      alert(completeMessage.MODIFY_CHARTER);
      navigate(`/project/${projectId}`);
      return;
    }
  } catch (error) {
    console.error(error);
    if (axios.isAxiosError(error)) {
      alert(`${ERROR_AT_CHARTER_REGISTER}`);
      alert(`${error.response?.data?.message}`);
      return;
    }
  }
};
