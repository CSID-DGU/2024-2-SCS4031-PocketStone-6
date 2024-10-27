import { API_URL } from '../constants/envText';
import { NO_EXCEL_FILE } from '../constants/errorMessage';
import { tokenAxios } from './tokenAPI';

// FormData 생성 및 서버로 전송
export const submitExcelData = async (
  e: React.FormEvent<HTMLFormElement>,
  file: File | null
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
  } catch (error) {
    console.error('파일 전송 실패:', error);
  }
};
