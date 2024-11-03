import React, { useState } from 'react';
import { submitExcelData } from '../../api/employee/submitExcelData';
import { useNavigate } from 'react-router-dom';

export const ExcelUploader = () => {
  const [file, setFile] = useState<File | null>(null);
  const navigate = useNavigate();

  // 파일 선택 시 상태 업데이트
  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) setFile(file);
  };

  return (
    <form
      onSubmit={(e) => {
        submitExcelData(e, file, navigate);
      }}
      encType="multipart/form-data"
    >
      <input type="file" accept=".xlsx, .xls" onChange={handleFileChange} />
      <button type="submit">파일 업로드</button>
    </form>
  );
};

export default ExcelUploader;
