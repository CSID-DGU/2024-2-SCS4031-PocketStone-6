from pydantic import BaseModel
from typing import List

# Request와 Response에 사용될 데이터 모델 정의
class Member(BaseModel):
    employeeId: int

class RecommendationRequestDto(BaseModel):
    projectId: int
    memberIds: List[int]

class RecommendationResponseDto(BaseModel):
    memberList: List[Member]