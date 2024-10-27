import { useNavigate } from "react-router-dom"
import { DatePickerComponent } from "../components/Input/DatePicker";
import { useLoginInfoQuery } from "../hooks/useLoginInfoQuery";
import { doLogout } from "../api/authAPI";
import { refreshPage } from "../utils/movePage";
import { useUserInfo } from "../hooks/useUserInfo";

export default function Test() {
    const navigate = useNavigate()
    const [isLogin, accessToken] = useLoginInfoQuery()
    const userInfoQuery = useUserInfo()
    return (
        <div>
            <button onClick={() => { navigate('/register') }}>회원가입</button>
            <button onClick={() => { navigate('/login') }}>로그인</button>
            <DatePickerComponent></DatePickerComponent>
            <p>{isLogin ? '로그인쿠키 있음' : '로그인쿠키 없음'}, {accessToken}</p>
            <p>로그인 정보: {JSON.stringify(userInfoQuery.data)}</p>
            <button onClick={() => {
                doLogout()
                refreshPage(navigate)
            }}>로그아웃</button>
        </div>
    )
}