import axios from 'axios';
import { tokenAxios } from '../tokenAPI';
import { API_URL } from '../../constants/envText';
import { headers } from '../../constants/headers';

export const getEmployeeSpec = async (employeeId: number) => {
  try {
    const response = await tokenAxios.get(`${API_URL}/api/employee/${employeeId}/spec`, {
      headers: headers,
    });
    return response.data;
  } catch (error) {
    console.error(error);
    if (axios.isAxiosError(error)) {
      return error.response?.data;
    }
  }
};
