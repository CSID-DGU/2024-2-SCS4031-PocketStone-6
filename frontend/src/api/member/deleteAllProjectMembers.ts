import axios from 'axios';
import { tokenAxios } from '../tokenAPI';
import { API_URL } from '../../constants/envText';
import { headers } from '../../constants/headers';
import { NavigateFunction } from 'react-router-dom';
import { refreshPage } from '../../utils/movePage';

export const deleteAllProjectMembers = async (projectId: number, navigate: NavigateFunction) => {
  try {
    const response = await tokenAxios.delete(`${API_URL}/api/member/${projectId}/all`, {
      headers: headers,
    });
    alert(response.data.message);
    navigate(`/project/${projectId}`);
  } catch (error) {
    console.error(error);
    if (axios.isAxiosError(error)) {
      alert(error.response?.data);
      refreshPage(navigate);
    }
  }
};
