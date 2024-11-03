import { NavigateFunction } from 'react-router-dom';
import { API_URL } from '../../constants/envText';
import {
  IS_DUPLICATED_EXCEL,
  NO_EXCEL_FILE,
} from '../../constants/errorMessage';
import { tokenAxios } from '../tokenAPI';
import { refreshPage } from '../../utils/movePage';

// FormData 생성 및 서버로 전송
export const submitExcelData = async (
  e: React.FormEvent<HTMLFormElement>,
  file: File | null,
  navigate: NavigateFunction
) => {
  e.preventDefault();

  if (!file) {
    alert(NO_EXCEL_FILE);
    return;
  }

  const formData = new FormData();
  formData.append('file', file);

  try {
    const response = await tokenAxios.post(
      `${API_URL}/api/employee/add-excel`,
      formData,
      {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      }
    );
    console.log('파일 전송 성공:', response.data);
    alert(response.data.message);
    refreshPage(navigate);
  } catch (error) {
    console.error('파일 전송 실패:', error);
    alert(IS_DUPLICATED_EXCEL);
    refreshPage(navigate);
  }
};
