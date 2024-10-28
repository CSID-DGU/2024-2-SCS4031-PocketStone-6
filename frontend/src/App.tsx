import { Route, Routes, useNavigate } from 'react-router-dom';
import './App.css';
import Main from './routes/Main';
import Register from './routes/auth/Register';
import Login from './routes/auth/Login';
import Test from './routes/Test';
import Project from './routes/project/Project';
import About from './routes/project/About';
import Employee from './routes/employee/Employee';
import { NewProject } from './routes/project/NewProject';

function App() {
  const navigate = useNavigate();

  return (
    <div>
      <div style={{ display: 'flex', alignItems: 'center' }}>
        <h2
          onClick={() => {
            navigate('/');
          }}
        >
          Team-Sync
        </h2>
        <button
          onClick={() => {
            navigate('/test');
          }}
        >
          테스트페이지 이동
        </button>
        <button
          onClick={() => {
            navigate('/project');
          }}
        >
          프로젝트 관리
        </button>
        <button
          onClick={() => {
            navigate('/employee');
          }}
        >
          인원 관리
        </button>
      </div>
      <Routes>
        {/* 메인 페이지 */}
        <Route path="/" element={<Main />} />

        {/* 테스트 페이지 */}
        <Route path="/test" element={<Test />} />

        {/* 회원가입/로그인 */}
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />

        {/* 프로젝트 */}
        <Route path="/project" element={<Project />} />
        <Route path="/project/about" element={<About />} />
        <Route path="/project/new" element={<NewProject />} />

        {/* 인원 관리 */}
        <Route path="/employee" element={<Employee />} />
      </Routes>
    </div>
  );
}

export default App;
