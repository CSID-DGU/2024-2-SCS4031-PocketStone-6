import { useEffect, useState } from "react";

export const useLoginInfoQuery = () => {
    // 로그인 정보 확인
    const [isLogin, setIsLogin] = useState(false)
    const [accessToken, setAccessToken] = useState("")
    
    useEffect(() => {
        const accessTokenCookie = document.cookie.split(';').find(cookie => cookie.trim().startsWith('access='));
        const accessTokenValue = accessTokenCookie ? accessTokenCookie.split('=')[1] : '';
        if (accessTokenCookie) setIsLogin(true)
        setAccessToken(accessTokenValue)
    }, [])

    return [isLogin, accessToken]
}
