# models.py
from sqlalchemy import Column, String, Float, Text, Enum, ForeignKey, BigInteger
from sqlalchemy.orm import relationship
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

    employee_id = Column(BigInteger, primary_key=True)
    name = Column(String(100))
    company_id = Column(BigInteger)
    skill_score = Column(Float)
    kpi_score = Column(Float)
    peer_evaluation_score = Column(Float)
    personal_type = Column(Text)
    role = Column(Enum(Role))  

    # Employee -> PastProject (한 사원이 참여한 여러 과거 프로젝트)
    pastProjects = relationship('PastProject', back_populates='employee')

class PastProject(Base):
    __tablename__ = "past_project"
    
    past_project_id = Column(BigInteger, primary_key=True)
    description = Column(String(255))
    project_name = Column(String(255))
    employee_id = Column(BigInteger, ForeignKey('employee.employee_id'))

    employee = relationship('Employee', back_populates='pastProjects')
    
    
class Project(Base):
    __tablename__ = "project"  # Spring Boot에서 생성된 테이블 이름과 일치해야 함

    id = Column(BigInteger, primary_key=True)
    company_id= Column(BigInteger)
    project_name = Column(String(255))
    
    
class ProjectCharter(Base):
    __tablename__ = "project_charter"
     
    id = Column(BigInteger, primary_key=True)
    project_id = Column(BigInteger, ForeignKey('project.id'), nullable=False) 
    
class Company(Base):
    __tablename__ = "company"  # Spring Boot에서 생성된 테이블 이름과 일치해야 함

    id = Column(BigInteger, primary_key=True)
    user_id = Column(BigInteger)

class Skill(enum.Enum):
    ADOBE_XD = "ADOBE_XD"
    AGILE = "AGILE"
    ANGULAR = "ANGULAR"
    DJANGO = "DJANGO"
    FAST_API = "FAST_API"
    FIGMA = "FIGMA"
    FLASK = "FLASK"
    GRAPHQL = "GRAPHQL"
    HTML_CSS = "HTML_CSS"
    IN_VISION = "IN_VISION"
    JAVASCRIPT = "JAVASCRIPT"
    JIRA = "JIRA"
    J_QUERY = "J_QUERY"
    NODE_JS = "NODE_JS"
    PYTHON = "PYTHON"
    R = "R"
    REACT = "REACT"
    ROADMAP = "ROADMAP"
    RUBY = "RUBY"
    SAS = "SAS"
    SASS = "SASS"
    SCRUM = "SCRUM"
    SKETCH = "SKETCH"
    SPRING_BOOT = "SPRING_BOOT"
    SPRING_FRAMEWORK = "SPRING_FRAMEWORK"
    SWOT_ANALYSIS = "SWOT_ANALYSIS"
    TREND_ANALYSIS = "TREND_ANALYSIS"
    TYPESCRIPT = "TYPESCRIPT"
    USER_STORIES = "USER_STORIES"
    VUE = "VUE"
    FLUTTER="FLUTTER"
    
class EmployeeSkill(Base):
    __tablename__ = "employee_skill"  # Spring Boot에서 생성된 테이블 이름과 일치해야 함

    employee_id = Column(BigInteger, primary_key=True)
    skill = Column(Enum(Skill), primary_key=True)  


# 추천용 사원 데이터 저장
class ScaledEmployee(Base_scaled):
    __tablename__ = "scaled_employee"
    
    employee_id = Column(BigInteger, primary_key=True, index=True)
    company_id = Column(BigInteger)
    skill_score = Column(Float)
    kpi_score = Column(Float)
    peer_evaluation_score = Column(Float)
    role =  Column(Enum(Role))
    personality_embedding = Column(Text)  # 성향 임베딩을 저장할 필드

# 프로젝트 적합도
class ScaledPastProject(Base_scaled):
    __tablename__ = "scaled_past_project"
    
    past_project_id = Column(BigInteger, primary_key=True, index=True)
    new_project_id =Column(BigInteger)
    employee_id = Column(BigInteger,ForeignKey('scaled_employee.employee_id'))
    project_fit_score = Column(Float)  # 프프로젝트임베딩 적합도
    role =  Column(Enum(Role))