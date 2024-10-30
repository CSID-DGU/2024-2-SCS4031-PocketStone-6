import { Route, Routes } from 'react-router-dom';
import S from './App.module.css';
import Main from './routes/Main';
import Register from './routes/auth/Register';
import Login from './routes/auth/Login';
import Test from './routes/Test';
import Project from './routes/project/Project';
import About from './routes/project/About';
import Employee from './routes/employee/Employee';
import { Navbar } from './components/Navbar';

function App() {
  return (
    <div>
      <Navbar />
      <div className={S.contentContainer}>
        <Routes>
          {/* 메인 페이지 */}
          <Route path="/" element={<Main />} />

          {/* 테스트 페이지 */}
          <Route path="/test" element={<Test />} />

          {/* 회원가입/로그인 */}
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />

          {/* 프로젝트 */}
          <Route path="/project" element={<Project />}>
            <Route path="new" element={<p>new</p>} />
          </Route>
          <Route path="/project/about" element={<About />} />

          {/* 인원 관리 */}
          <Route path="/employee" element={<Employee />} />
        </Routes>
      </div>
    </div>
  );
}

export default App;
