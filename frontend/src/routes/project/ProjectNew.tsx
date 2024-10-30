import { useState } from 'react';
import { createProject } from '../../api/projects/createProject';
import { useNavigate } from 'react-router-dom';

export default function ProjectNew() {
  const [projectName, setProjectName] = useState('');
  const [startDate, setStartDate] = useState('');
  const [mvpDate, setMvpDate] = useState('');
  const navigate = useNavigate();
  return (
    <div>
      <h2>프로젝트 등록</h2>
      <input
        type="text"
        onChange={(e) => {
          setProjectName(e.target.value);
        }}
      ></input>
      <input
        type="text"
        onChange={(e) => {
          setStartDate(e.target.value);
        }}
      ></input>
      <input
        type="text"
        onChange={(e) => {
          setMvpDate(e.target.value);
        }}
      ></input>
      <button
        onClick={() => {
          createProject(projectName, startDate, mvpDate, navigate);
        }}
      >
        프로젝트 등록
      </button>
    </div>
  );
}
