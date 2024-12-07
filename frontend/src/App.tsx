import { Route, Routes } from 'react-router-dom';
import Main from './routes/Main';
import Register from 'routes/auth/Register';
import Login from './routes/auth/Login';
import Test from './routes/Test';
import Project from './routes/project/Project';
import Employee from './routes/employee/Employee';
import { Navbar } from './components/Navbar/Navbar';
import ProjectNew from './routes/project/ProjectNew';
import ProjectDetail from './routes/project/ProjectDetail';
import EmployeeDetail from './routes/employee/EmployeeDetail';
import S from 'App.module.css';
import ProjectMember from 'routes/project/ProjectMember';
import { useLoginInfoQuery } from 'hooks/useLoginInfoQuery';
import BeforeLogin from 'routes/BeforeLogin';
import ProjectCharter from 'routes/project/ProjectCharter';
import ProjectTimelines from 'routes/project/ProjectTimelines';
import History from 'routes/history/History';
import Evaluation from 'routes/history/evaluation/Evaluation';

function App() {
  const { isLogin } = useLoginInfoQuery();

  return <>{isLogin ? <AfterLoginApp /> : <BeforeLoginApp />}</>;
}

const BeforeLoginApp = () => {
  return (
    <div className={S.beforeContainer}>
      <div>
        <Routes>
          <Route path="*" element={<BeforeLogin />} />
          {/* 회원가입/로그인 */}
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />
        </Routes>
      </div>
    </div>
  );
};

const AfterLoginApp = () => {
  return (
    <div className={S.appContainer}>
      <Navbar />
      <div>
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
          <Route path="/project/new" element={<ProjectNew />} />
          <Route path="/project/:id" element={<ProjectDetail />} />
          <Route path="/project/:id/charter" element={<ProjectCharter />} />
          <Route path="/project/:id/timelines" element={<ProjectTimelines />} />
          <Route path="/project/:id/member" element={<ProjectMember />} />

          {/* 이전 프로젝트 */}
          <Route path="/history" element={<History />} />
          <Route path="/history/:id/evaluation" element={<Evaluation />} />

          {/* 인원 관리 */}
          <Route path="/employee" element={<Employee />} />
          <Route path="/employee/:id" element={<EmployeeDetail />} />
        </Routes>
      </div>
    </div>
  );
};

export default App;
