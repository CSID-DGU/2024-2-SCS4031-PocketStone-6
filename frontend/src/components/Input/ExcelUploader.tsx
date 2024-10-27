import React, { useState } from 'react';
import { handleSubmit } from '../../api/handleFileChange';

export const ExcelUploader = () => {
  const [file, setFile] = useState<File | null>(null);

  // 파일 선택 시 상태 업데이트
  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) setFile(file);
  };

  return (
    <form
      onSubmit={(e) => {
        handleSubmit(e, file);
      }}
      encType="multipart/form-data"
    >
      <input type="file" accept=".xlsx, .xls" onChange={handleFileChange} />
      <button type="submit">파일 업로드</button>
    </form>
  );
};

export default ExcelUploader;
