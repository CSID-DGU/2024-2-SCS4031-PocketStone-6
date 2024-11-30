import { useState } from 'react';
import { createProject } from '../../api/projects/createProject';
import { useNavigate } from 'react-router-dom';
import S from './ProjectNew.module.scss';
import { MS, IS, BS } from 'styles';
import { parseDateToString } from 'utils/parseDate';
import DatePicker from 'react-datepicker';
import { isMonday, isSunday } from 'utils/filterDate';

export default function ProjectNew() {
  const [projectName, setProjectName] = useState('');
  const [startDate, setStartDate] = useState<Date | null>(null);
  const [mvpDate, setMvpDate] = useState<Date | null>(null);
  const navigate = useNavigate();
  return (
    <div className={MS.container}>
      <div className={MS.content}>
        <div className={MS.contentTitle}>
          <p>새 프로젝트 등록</p>
        </div>
        <div className={MS.contentBox}>
          <input
            className={`${IS.textInput} ${S.projectNameInput}`}
            type="text"
            placeholder="프로젝트의 제목을 입력해주세요."
            onChange={(e) => {
              setProjectName(e.target.value);
            }}></input>
          <div className={S.dateInputDiv}>
            <div className={S.dateInputDiv}>
              <DatePicker
                className={`${IS.textInput} ${MS.width100}`}
                filterDate={(date)=>isMonday(date, mvpDate)}
                placeholderText="시작일"
                selected={startDate}
                onChange={(date) => setStartDate(date)}
                dateFormat="yyyy-MM-dd"
              />
            </div>
            <p>~</p>
            <div className={S.dateInputDiv}>
              <DatePicker
                className={`${IS.textInput} ${MS.width100}`}
                filterDate={(date)=>isSunday(date, startDate)}
                placeholderText="종료일"
                selected={mvpDate}
                onChange={(date) => setMvpDate(date)}
                dateFormat="yyyy-MM-dd"
              />
            </div>
          </div>
          <div className={S.btnsDiv}>
            <button
              className={BS.WhiteBtn}
              onClick={() => {
                navigate('/project');
              }}>
              돌아가기
            </button>
            <button
              className={BS.YellowBtn}
              onClick={() => {
                createProject(
                  projectName,
                  parseDateToString(startDate),
                  parseDateToString(mvpDate),
                  navigate
                );
              }}>
              프로젝트 등록
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
