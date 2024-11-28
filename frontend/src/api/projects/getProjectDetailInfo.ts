import axios from 'axios';
import { tokenAxios } from '../tokenAPI';
import { API_URL } from '../../constants/envText';
import { headers } from '../../constants/headers';

export const getProjectBasicInfo = async (id: number) => {
  try {
    const response = await tokenAxios.get(`${API_URL}/api/projects/id/${id}`, {
      headers: headers,
    });
    // console.log(response);
    return response.data;
  } catch (error) {
    console.error(error);
    if (axios.isAxiosError(error)) {
      return error.response?.data;
    }
  }
};

export const getProjectCharter = async (id: number) => {
  try {
    const response = await tokenAxios.get(`${API_URL}/api/projects/charter/${id}`, {
      headers: headers,
    });
    return response.data;
  } catch (error) {
    console.error(error);
    if (axios.isAxiosError(error)) {
      return {};
    }
  }
};

export const getProjectTimelines = async (id: number) => {
  try {
    const response = await tokenAxios.get(`${API_URL}/api/projects/timelines/${id}`, {
      headers: headers,
    });
    return response.data;
  } catch (error) {
    console.error(error);
    if (axios.isAxiosError(error)) {
      return [];
    }
  }
};
