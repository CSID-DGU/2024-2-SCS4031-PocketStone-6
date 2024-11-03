import axios from 'axios';
import { NavigateFunction } from 'react-router-dom';
import { API_URL } from '../../constants/envText';
import { headers } from '../../constants/headers';
import { WRONG_LOGIN_INFO } from '../../constants/errorMessage';
import {
  deleteCookies,
  getCookie,
  saveCookies,
} from '../../utils/handleCookies';

export const doLogin = async (
  id: string,
  password: string,
  navigate: NavigateFunction
) => {
  const content: loginInfoType = {
    loginId: id,
    password: password,
  };

  // 로그인 시도
  try {
    const response = await axios.post(`${API_URL}/api/auth/login`, content, {
      headers: headers,
    });
    const { accessToken, refreshToken } = response.data;
    saveCookies('access', accessToken);
    saveCookies('refresh', refreshToken);
    navigate('/');
  } catch (error) {
    alert(WRONG_LOGIN_INFO);
    if (axios.isAxiosError(error)) {
      return error.response?.data;
    }
  }
};

export const doRegister = async (
  id: string,
  email: string,
  password: string,
  companyName: string,
  navigate: NavigateFunction
) => {
  const content: registerInfoType = {
    loginId: id,
    email: email,
    password: password,
    companyName: companyName,
  };

  try {
    const response = await axios.post(`${API_URL}/api/auth/signup`, content, {
      headers: headers,
    });
    alert(response.data.message)
    navigate('/login');
  } catch (error) {
    if (axios.isAxiosError(error)) {
      return error.response?.data;
    }
  }
};

export const checkID = async (id: string) => {
  const content: checkIDType = {
    loginId: id,
  };

  try {
    const response = await axios.post(
      `${API_URL}/api/users/check-loginid`,
      content,
      { headers: headers }
    );
    alert(response.data.message)
  } catch (error) {
    console.error(error);
    if (axios.isAxiosError(error)) {
      return error.response?.data;
    }
  }
};

export const doLogout = () => {
  deleteCookies('access');
  deleteCookies('refresh');
};

export const getAccessToken = async () => {
  const content: { refreshToken: string | null } = {
    refreshToken: getCookie('refresh'),
  };

  try {
    const response = await axios.post(`${API_URL}/api/auth/refresh`, content, {
      headers: headers,
    });
    return response.data;
  } catch (error) {
    console.error(error);
    if (axios.isAxiosError(error)) {
      console.error(error.response?.data);
    }
    return Promise.reject(error);
  }
};
