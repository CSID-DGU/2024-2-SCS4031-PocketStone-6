import { useNavigate } from "react-router-dom"
import { DatePickerComponent } from "../components/Input/DatePicker";

export default function Test() {
    const navigate = useNavigate()
    return (
        <div>
            <button onClick={() => { navigate('/register') }}>회원가입</button>
            <button onClick={() => { navigate('/login') }}>로그인</button>
            <DatePickerComponent></DatePickerComponent>
        </div>

    )
}