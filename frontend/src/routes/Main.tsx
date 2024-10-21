import { useLoginInfo } from "../hooks/useLoginInfo"

export default function Main() {

  const [isLogin, accessToken] = useLoginInfo()

  return (
    <div>
      로그인 전 메인페이지임
      <p>{isLogin ? '있음' : '없음'}, {accessToken}</p>
    </div>
  )
}