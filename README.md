# 🪨 2024-2-SCS4031-PocketStone-6

## 🧑‍💼 사내 데이터를 이용한 프로젝트 팀빌딩 시스템 [ Team-Sync ]

<table align="center">
    <tr align="center">
        <td colspan="4">
            <p style="font-size: x-large; font-weight: bold;">2024년 2학기 융합캡스톤디자인 6조 돌주머니</p>
        </td>
    </tr>
    <tr align="center">
        <td style="min-width: 150px;">
            <a href="https://github.com/thisis-hee">
                <img src="https://avatars.githubusercontent.com/u/143998370?v=4" width="200" alt="이건희_깃허브프로필" />
                <br />
                <b>gunheee-leee</b>
            </a>
        </td>
        <td style="min-width: 150px;">
            <a href="https://github.com/Minbro-Kim">
                <img src="https://avatars.githubusercontent.com/u/144206885?v=4" width="200" alt="김민형_깃허브프로필">
                <br />
                <b>Minbro-Kim</b>
            </a>
        </td>
        <td style="min-width: 150px;">
            <a href="https://github.com/BrianKim913">
                <img src="https://avatars.githubusercontent.com/u/119075610?v=4" width="200" alt="김범수_깃허브프로필">
                <br />
                <b>BrianKim913</b>
            </a>
        </td>
        <td style="min-width: 150px;">
            <a href="https://github.com/MinSungJe">
                <img src="https://avatars.githubusercontent.com/u/101497652?v=4" width="200" alt="성민제_깃허브프로필">
                <br />
                <b>MinSungJe</b>
            </a>
        </td>
    </tr>
    <tr align="center">
        <td>
            <b>이건희</b>
        </td>
        <td>
            <b>김민형</b>
        </td>
        <td>
            <b>김범수</b>
        </td>
        <td>
            <b>성민제</b>
        </td>
    </tr>
    <tr align="center">
        <td>
            <b>Team Leader</b>, Model
        </td>
        <td>
            Backend
        </td>
        <td>
            Backend
        </td>
        <td>
            Frontend
        </td>
    </tr>
</table>



## 💿 프로젝트 실행 방법

### 1. 프로젝트 clone 받기
```bash
git clone https://github.com/CSID-DGU/2024-2-SCS4031-PocketStone-6.git
```

### 2. backend/team-sync 👉 데이터 베이스 주소 변경
- `backend\team-sync\src\main\resources\application.properties` 
   - `spring.datasource.url`, `spring.datasource.username`, `spring.datasource.password` 수정
   - 수정 내용은 이메일 참조(데이터베이스 주소)

### 3. backend/team-model 👉 .env 파일 추가
- `backend\team-model\app\`에 `.env` 파일 추가
    - 내용은 이메일 참조(OPENAPI KEY)

### 4. backend/team-model 👉 데이터 베이스 주소 변경
- `backend\team-model\app\database.py`
    - `DATABASE_URL`, `SCALED_DATABASE_URL` 수정
    - 수정 내용은 이메일 참조(데이터베이스 주소)

### 5. backend 파일 실행
- 모델 서버 실행
    ```bash
    (메인 폴더 위치에서)
    cd backend/team-model/app
    pip install uvicorn sqlalchemy==1.4.39 pymysql==1.1.0 fastapi
    uvicorn main:app --reload
    ```

- SPRINGBOOT 서버 실행
    ```bash
    (메인 폴더 위치에서)
    cd backend/team-sync
    ./gradlew clean build
    cd build/libs
    java -jar team-sync-0.0.1-SNAPSHOT.jar
    ```

### 6. frontend 👉 .env 파일 추가
- `frontend\`에 `.env` 파일 추가
    - 내용: `REACT_APP_API_URL=http://localhost:8080`

### 7. frontend 파일 실행
```bash
(메인 폴더 위치에서)
cd frontend
npm install
npm start
```

### 8. 로그인 계정
- ID: `pocketstone`
- PW: `pocket123`

## 🔎 살펴보기

<details>
<summary><b>🖥️ (필요시)서버 주소 세부조정</b></summary>

 - `WebClientConfig.java`
   - baseUrl()을 해당 fastapi 서버로 수정
- `WebSecurityConfig.java`
   - 61번째줄 코드`(configuration.setAllowedOrigins(Arrays.asList)`를 리액트 주소로 수정
</details>
<details>
<summary><b>🎯 Commit Convention</b></summary>

- <b>구성</b>
    ```
    {역할}: [{키워드}] {내용}
    ```
    - 역할 -  `Model`, `BE`, `FE`
    - 예시 - `FE: [feat] 회원가입 페이지 구성`
- <b>키워드</b>

    |키워드|내용|
    |---|---|
    |feat|새로운 기능 추가|
    |fix|버그 수정|
    |docs|문서 수정|
    |style|코드 포맷팅, 세미콜론 누락 등 코드 변경이 없는 경우
    |refactor|코드 리펙토링|
    |test|테스트 코드, 리펙토링 테스트 코드 추가|
    |chore|빌드 업무 수정, 패키지 매니저 수정(npm, .gitignore 등)
    |remove|파일 삭제|
    |rename|파일 이름 변경|
</details>

