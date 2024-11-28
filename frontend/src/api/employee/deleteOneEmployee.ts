import axios from 'axios';
import { tokenAxios } from '../tokenAPI';
import { API_URL } from '../../constants/envText';
import { headers } from '../../constants/headers';
import { ERROR_AT_DELETE_EMPLOYEE } from 'constants/errorMessage';

export const deleteOneEmployee = async (employeeId: number) => {
  try {
    const response = await tokenAxios.delete(
      `${API_URL}/api/employee/${employeeId}`,
      {
        headers: headers,
      }
    );
    alert(response.data.message);
  } catch (error) {
    console.error(error);
    if (axios.isAxiosError(error)) {
      alert(ERROR_AT_DELETE_EMPLOYEE);
    }
  }
};
