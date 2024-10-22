import { useNavigate } from "react-router-dom"
import { DatePickerComponent } from "../components/Input/DatePicker";
import { useLoginInfo } from "../hooks/useLoginInfo";
import { doLogout } from "../api/authAPI";

export default function Test() {
    const navigate = useNavigate()
    const [isLogin, accessToken] = useLoginInfo()
    return (
        <div>
            <button onClick={() => { navigate('/register') }}>회원가입</button>
            <button onClick={() => { navigate('/login') }}>로그인</button>
            <DatePickerComponent></DatePickerComponent>
            <p>{isLogin ? '있음' : '없음'}, {accessToken}</p>
            <button onClick={() => { doLogout() }}>로그아웃</button>
        </div>

    )
}