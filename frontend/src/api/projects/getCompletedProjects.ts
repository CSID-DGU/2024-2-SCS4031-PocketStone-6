import axios from 'axios';
import { tokenAxios } from '../tokenAPI';
import { API_URL } from '../../constants/envText';
import { headers } from '../../constants/headers';

export const getCompletedProjects = async () => {
  try {
    const response = await tokenAxios.get(`${API_URL}/api/projects`, {
      headers: headers,
      params: {
        status: 'COMPLETED'
      }
    });
    return response.data;
  } catch (error) {
    console.error(error);
    if (axios.isAxiosError(error)) {
      return error.response?.data;
    }
  }
};
