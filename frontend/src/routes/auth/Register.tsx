import { useState } from "react"
import { checkID } from "../../api/authAPI"

export default function Register() {
    const [id, setId] = useState('')
    const [password, setPassword] = useState('')
    return (
        <div>
            <input type="text" onChange={(e) => { setId(e.target.value) }}></input>
            <input type="password" onChange={(e) => { setPassword(e.target.value) }}></input>
            <button onClick={()=>{
                checkID(id)
            }}>아이디 중복 확인</button>
        </div>
    )
}