import axios from 'axios';
import { tokenAxios } from '../tokenAPI';
import { API_URL } from '../../constants/envText';
import { headers } from '../../constants/headers';
import { NavigateFunction } from 'react-router-dom';
import { refreshPage } from '../../utils/movePage';

export const deleteAllEmployInfo = async (navigate: NavigateFunction) => {
  try {
    const response = await tokenAxios.delete(`${API_URL}/api/employee`, {
      headers: headers,
    });
    alert(response.data.message);
    refreshPage(navigate);
  } catch (error) {
    console.error(error);
    if (axios.isAxiosError(error)) {
      alert(error.response?.data);
      refreshPage(navigate);
    }
  }
};
