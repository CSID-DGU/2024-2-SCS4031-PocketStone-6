import { Link, Route, Routes } from 'react-router-dom';
import './App.css';
import Main from './routes/Main';
import Register from './routes/auth/Register';
import Login from './routes/auth/Login';
import Test from './routes/Test';

function App() {
  return (
    <div>
      <div style={{ display: 'flex' }}>
        <h2>페이지 모든 Nav바임</h2>
        <Link to={`/test`}>테스트페이지 이동</Link>
      </div>
      <Routes>
        {/* 메인 페이지 */}
        <Route path='/' element={<Main />} />

        {/* 테스트 페이지 */}
        <Route path='/test' element={<Test />} />

        {/* 회원가입/로그인 */}
        <Route path='/register' element={<Register />} />
        <Route path='/login' element={<Login />} />
      </Routes>
    </div>
  );
}

export default App;