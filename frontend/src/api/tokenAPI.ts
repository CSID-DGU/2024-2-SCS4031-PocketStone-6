import axios from 'axios';
import { getCookie, saveCookies } from '../utils/handleCookies';
import { getAccessToken } from './auth/authAPI';

// Axios 인스턴스 생성
export const tokenAxios = axios.create();

tokenAxios.interceptors.request.use(
  async (config) => {
    const accessTokenforAPI = getCookie('access');

    // AccessToken이 있다면 헤더에 토큰 추가
    if (accessTokenforAPI) {
      config.headers = config.headers || {};
      config.headers.Authorization = `Bearer ${accessTokenforAPI}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

tokenAxios.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    if (error.response) {
      const errorStatus = error.response.data.status;
      const errorMessage = error.response.data.message;

      switch (errorStatus) {
        case 403:
          try {
            console.error('Access Token 만료, ' + errorMessage);
            const newAccessToken = await getAccessToken(); // AccessToken 새로 불러오기
            saveCookies('access', newAccessToken);
            console.log('새로운 Access Token : ' + newAccessToken);

            // header 설정 후 재실행
            originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
            return tokenAxios(originalRequest);
          } catch (error) {
            console.error(error);
            if (axios.isAxiosError(error)) {
              return error.response?.data;
            }
          }
      }
    }
    return Promise.reject(error);
  }
);
