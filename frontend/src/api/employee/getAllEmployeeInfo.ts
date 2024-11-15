import axios from 'axios';
import { tokenAxios } from '../tokenAPI';
import { API_URL } from '../../constants/envText';
import { headers } from '../../constants/headers';

export const getAllEmployeeInfo = async () => {
  try {
    const response = await tokenAxios.get(`${API_URL}/api/employee`, {
      headers: headers,
    });

    if (response.data === undefined || response.data === "") return [];
    return response.data;
  } catch (error) {
    console.error(error);
    if (axios.isAxiosError(error)) {
      return [];
    }
  }
};
