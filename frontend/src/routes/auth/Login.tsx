import { useState } from "react"

export default function Login() {
    const [id, setId] = useState('')
    const [password, setPassword] = useState('')
    return (
        <div>
            <input type="text" onChange={(e) => { setId(e.target.value) }}></input>
            <input type="password" onChange={(e) => { setPassword(e.target.value) }}></input>
            <button onClick={()=>{
                console.log(id, password)
            }}>로그인</button>
        </div>
    )
}