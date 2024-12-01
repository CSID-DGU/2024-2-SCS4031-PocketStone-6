import axios from 'axios';
import { tokenAxios } from '../tokenAPI';
import { API_URL } from '../../constants/envText';
import { headers } from '../../constants/headers';
import { completeMessage } from 'constants/completeMessage';
import { refreshPage } from 'utils/movePage';
import { NavigateFunction } from 'react-router-dom';

export const deleteProject = async (id: number, navigate: NavigateFunction) => {
  try {
    await tokenAxios.delete(`${API_URL}/api/projects/${id}`, {
      headers: headers,
    });
    alert(completeMessage.DELETE_PROJECT)
    refreshPage(navigate)
  } catch (error) {
    console.error(error);
    if (axios.isAxiosError(error)) {
      alert(error.response?.data);
    }
  }
};
