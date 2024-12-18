from fastapi import FastAPI, Depends 
from sqlalchemy.orm import Session
from database import get_db, Base_scaled, scaled_engine, get_db_scaled
import random
import logging
from employee_service import scale_employee_data  # scale_employee_data 함수 임포트
from schemas import Member, RecommendationRequestDto, RecommendationResponseDto
from models import Employee, Company, Project
from personality_service import embedding_employee_personality
from project_service  import embedding_project
from recommendation_service import recommend_team

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

app = FastAPI()

# 테이블 생성 (애플리케이션 실행 시 자동으로 테이블이 생성됩니다)
Base_scaled.metadata.create_all(bind=scaled_engine)

@app.get("/")
def read_root():
    return {"message": "Hello, Wosdsdsdsdsrld!"}

@app.get("/employees")
def read_employees(db: Session = Depends(get_db)):
    employees = db.query(Employee).all()
    return employees

# 특정 회사 사원 점수 정규화 하기 
@app.post("/api/scale-employee-score/") #쿼리파라미터로 회사아이디 보내기?company_id=1
def scale_employee_data_endpoint(company_id:int, db: Session = Depends(get_db)):
    print(f"점수 스케일링시작!!!!!!: {company_id}")
    result = scale_employee_data(db,company_id)
    return {"message": result}

# 성향 임베딩 저장하기
@app.post("/api/embedding-employee-personality/") #쿼리파라미터로 회사아이디 보내기?company_id=1
def embedding_personality_data_endpoint(company_id:int, db: Session = Depends(get_db)):
    print(f"성향 임베딩 시작!!!!!!: {company_id}")
    result = embedding_employee_personality(db,company_id)
    return {"message": result}

# 과거 프로젝트 적합도 임베딩하기
@app.post("/api/embedding-project/") #쿼리파라미터로 회사아이디 보내기?company_id=1
def embedding_project_data_endpoint(company_id:int,project_id:int, db: Session = Depends(get_db)):
    print(f"프로젝트 임베딩 시작!!!!!!: {project_id}")
    result = embedding_project(db,company_id=company_id, project_id=project_id)
    return {"message": result}

"""
# 추천
@app.post("/api/recommendation-team/") #쿼리파라미터로 회사아이디 보내기?project_id=1
def recommend_project_team_endpoint(company_id:int,project_id:int, db: Session = Depends(get_db), scaled_db: Session=Depends(get_db_scaled)):
    result = recommend_team(db=db,company_id=company_id, project_id=project_id,scaled_db=scaled_db)
    return result
"""

@app.post("/api/recommendation/")
def recommendation( company_id:int, body: RecommendationRequestDto, scaled_db: Session=Depends(get_db_scaled),db: Session = Depends(get_db)):
    project_id = body.projectId
    weightType = body.weightType
    memberIds = body.memberIds
    print("추천 API 시작")

    logger.info("================== FUNCTION START ==================")
    logger.info(f"Raw body: {body}")
    logger.info(f"Project ID: {body.projectId}")
    logger.info(f"Member IDs: {body.memberIds}")
    logger.info(f"weight type:{weightType}")
    
    result = recommend_team(memberIds=memberIds, db=db,company_id=company_id, project_id=project_id,scaled_db=scaled_db, weightType=weightType)
    return result
    """
    # 프로젝트 ID로 해당 프로젝트 조회
    project = db.query(Project).filter(Project.id == project_id).first()

    if not project:
        return {"error": "해당 프로젝트를 찾을 수 없습니다."}

    # 프로젝트의 유저 ID를 통해 회사 조회
    company_id = project.company_id
    company = db.query(Company).filter(Company.id == company_id).first()

    if not company:
        return {"error": "해당 프로젝트와 연결된 회사를 찾을 수 없습니다."}

    # 해당 회사에 속한 직원들 조회
    company_id = company.id
    
    # 요청된 멤버들이 해당 회사에 속해 있는지 확인
    valid_members = db.query(Employee).filter(
        Employee.company_id == company_id,
        Employee.employee_id.in_(memberIds)
    ).all()

    # 만약 유효하지 않은 멤버가 있다면 오류 반환
    if len(valid_members) != len(memberIds):
        return {"error": "요청된 멤버 중 일부가 해당 회사에 속하지 않습니다."}
   
    print("valid members")
    print(valid_members)
    """
    



