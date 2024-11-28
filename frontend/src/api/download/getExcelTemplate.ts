import axios from 'axios';
import { tokenAxios } from '../tokenAPI';
import { API_URL } from '../../constants/envText';
import { headers } from '../../constants/headers';

export const getExcelTemplate = async (type: 'employee' | 'applicant') => {
  try {
    const response = await tokenAxios.get(`${API_URL}/api/${type}/download-template`, {
      headers: headers,
      responseType: 'blob',
    });

    // Blob 데이터를 URL로 변환
    const url = window.URL.createObjectURL(new Blob([response.data]));

    // 가상의 링크를 생성하여 다운로드 실행
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', `${type}-template.xlsx`);
    document.body.appendChild(link);
    link.click();

    // URL 객체와 링크 제거
    window.URL.revokeObjectURL(url);
    link.remove();
  } catch (error) {
    console.error(error);
    if (axios.isAxiosError(error)) {
      return error.response?.data;
    }
  }
};
