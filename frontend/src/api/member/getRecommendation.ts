import axios from 'axios';
import { tokenAxios } from '../tokenAPI';
import { API_URL } from '../../constants/envText';
import { headers } from '../../constants/headers';
import { getRecommendCategory } from 'utils/getRecommendCategory';

export const getRecommendation = async (projectId: number, category: number) => {
  try {
    const response = await tokenAxios.get(`${API_URL}/api/member/${projectId}/recommendation`, {
      headers: headers,
      params: { weightType: getRecommendCategory(category) },
    });
    return response.data;
  } catch (error) {
    console.error(error);
    if (axios.isAxiosError(error)) {
      return error.response?.data;
    }
  }
};
