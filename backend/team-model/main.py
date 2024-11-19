from fastapi import FastAPI
from pydantic import BaseModel
from typing import List

app = FastAPI()

# Request와 Response에 사용될 데이터 모델 정의
class RecommendationRequestDto(BaseModel):
    projectId: int
    member: int

class RecommendationResponseDto(BaseModel):
    employee: str


@app.get("/")
def read_root():
    return {"message": "Hello, World!"}

@app.post("/api/recommendation")
def recommendation(body: RecommendationRequestDto):
    project_id = body.projectId
    member_id = body.member
    print("여긲까지지ㅣ지지짖")

    # 임의의 로직을 통해 추천할 직원을 결정 (예제에서는 하드코딩)
    if project_id == 1 and member_id == 5:
        recommended_employee = "김민형"
    else:
        recommended_employee = "기본 직원"
    print("여기도 된다요")
    # RecommendationResponseDto 객체 생성
    response = RecommendationResponseDto(employee=recommended_employee)
    return response


