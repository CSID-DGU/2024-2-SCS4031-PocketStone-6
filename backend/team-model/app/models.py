# models.py
from sqlalchemy import Column, Integer, String, Float, Text, Enum
from database import Base,Base_scaled
import enum

# Role Enum 정의
class Role(enum.Enum):
    BACKEND_DEVELOPER = "BACKEND_DEVELOPER"
    DATA_ANALYST = "DATA_ANALYST"
    FRONTEND_DEVELOPER = "FRONTEND_DEVELOPER"
    PRODUCT_MANAGER = "PRODUCT_MANAGER"
    UI_UX_DESIGNER = "UI_UX_DESIGNER"

# Spring Boot에서 생성된 Employee 테이블 모델 정의
class Employee(Base):
    __tablename__ = "employee"  # Spring Boot에서 생성된 테이블 이름과 일치해야 함

    employee_id = Column(Integer, primary_key=True, index=True)
    name = Column(String(100))
    company_id = Column(Integer)
    kpi_score = Column(Float)
    peer_evaluation_score = Column(Float)
    personal_type = Column(Text)
    role = Column(Enum(Role))  

class Project(Base):
    __tablename__ = "project"  # Spring Boot에서 생성된 테이블 이름과 일치해야 함

    id = Column(Integer, primary_key=True, index=True)
    company_id= Column(Integer)

class Company(Base):
    __tablename__ = "company"  # Spring Boot에서 생성된 테이블 이름과 일치해야 함

    id = Column(Integer, primary_key=True, index=True)
    user_id = Column(Integer)
    

# 추천용 데이터 저장
class ScaledEmployee(Base_scaled):
    __tablename__ = "scaled_employee"
    
    employee_id = Column(Integer, primary_key=True, index=True)
    company_id = Column(Integer)
    kpi_score = Column(Float)
    peer_evaluation_score = Column(Float)
    personal_type = Column(Text)
    role = Column(String(255))
    personality_embedding = Column(Text)  # 성향 임베딩을 저장할 필드


