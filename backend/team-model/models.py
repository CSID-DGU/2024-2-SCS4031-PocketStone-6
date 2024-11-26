# models.py
from sqlalchemy import Column, Integer, String
from database import Base

# Spring Boot에서 생성된 Employee 테이블 모델 정의
class Employee(Base):
    __tablename__ = "employee"  # Spring Boot에서 생성된 테이블 이름과 일치해야 함

    employee_id = Column(Integer, primary_key=True, index=True)
    name = Column(String(100))
    company_id = Column(Integer)

class Project(Base):
    __tablename__ = "project"  # Spring Boot에서 생성된 테이블 이름과 일치해야 함

    id = Column(Integer, primary_key=True, index=True)
    user_id= Column(Integer)

class Company(Base):
    __tablename__ = "company"  # Spring Boot에서 생성된 테이블 이름과 일치해야 함

    id = Column(Integer, primary_key=True, index=True)
    user_id = Column(Integer)


