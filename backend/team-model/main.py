from fastapi import FastAPI, Depends 
from pydantic import BaseModel
from typing import List
from sqlalchemy.orm import Session
# main.py에서 상대 경로 대신 절대 경로 사용
from database import get_db

from models import Employee

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

@app.get("/employees")
def read_employees(db: Session = Depends(get_db)):
    employees = db.query(Employee).all()
    return employees




@app.post("/api/recommendation")
def recommendation(body: RecommendationRequestDto):
    project_id = body.projectId
    member_id = body.member
    print("여긲까지지ㅣ지지짖")

    
    if project_id == 1 and member_id == 5:
        recommended_employee = "김민형"
    else:
        recommended_employee = "기본 직원"
    print("여기도 된다요")
    # RecommendationResponseDto 객체 생성
    response = RecommendationResponseDto(employee=recommended_employee)
    return response


