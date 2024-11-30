import React, { useState } from 'react';
import { submitExcelData } from '../../api/employee/submitExcelData';
import { useNavigate } from 'react-router-dom';
import { BS } from 'styles';
import LoadingModal from 'components/Modal/LoadingModal';

export const ExcelUploader = () => {
  const [file, setFile] = useState<File | null>(null);
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  // 파일 선택 시 상태 업데이트
  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) setFile(file);
  };

  // 요청 시 로딩띄우기
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!file) {
      alert('파일을 선택해주세요.');
      return;
    }

    setLoading(true); // 로딩 시작
    try {
      await submitExcelData(e, file, navigate);
    } catch (error) {
      console.error('파일 업로드 실패:', error);
      alert('업로드 중 문제가 발생했습니다.');
    } finally {
      setLoading(false); // 로딩 종료
    }
  };

  return (
    <>
      {loading && <LoadingModal />}
      <form onSubmit={handleSubmit} encType="multipart/form-data">
        <input
          type="file"
          accept=".xlsx, .xls"
          onChange={handleFileChange}
          style={{ padding: '10px 0px' }}
        />
        <button className={BS.YellowBtn} type="submit">
          파일 업로드
        </button>
      </form>
    </>
  );
};

export default ExcelUploader;
