from sqlalchemy.orm import Session
from sklearn.preprocessing import MinMaxScaler
from models import Employee, PastProject, Objective,ProjectCharter,ScaledPastProject
import pandas as pd
from database import get_db_scaled
from dotenv import load_dotenv
import os
from openai import OpenAI
import openai
from sklearn.decomposition import PCA
from scipy.spatial.distance import cosine
from sqlalchemy import select

load_dotenv()
openai.api_key = os.getenv('OPENAI_API_KEY')

client = OpenAI(
    api_key=os.getenv("UPSTAGE_API_KEY"),
    base_url="https://api.upstage.ai/v1/solar"
)

# 데이터베이스에서 특정 회사의 Employee의 성향 데이터 가져오는 함수
def get_past_projects_by_company(db: Session, company_id: int):
    # 주어진 company_id에 해당하는 모든 사원의 과거 프로젝트 가져오기, 사원정보 제외
    projects = db.query(PastProject).join(Employee).filter(Employee.company_id == company_id).with_entities(PastProject).all()
    return projects
    
# 진행할 프로젝트 가져오기
def get_objective_by_project_id(db: Session, project_id: int):
    # `project_id` -> `project_charter_id` -> `objective_name`, `objective_content` 가져옴
    results = db.query(Objective.objective_name, Objective.objective_content)\
        .join(ProjectCharter, ProjectCharter.id == Objective.project_charter_id)\
        .filter(ProjectCharter.project_id == project_id).all()
    
    # 목표 이름과 내용을 "; "로 구분하고 문자열로 반환하기
    objectives_string = "; ".join([f"{name}: {content}" for name, content in results])
    return objectives_string

# 데이터베이스에서 사원 역할 가져오기
def get_employee_role_by_id(db: Session, employee_id: int):
    query = select(Employee.role).where(Employee.employee_id == employee_id)
    
    result = db.execute(query).scalars().first()  # 첫 번째 값만 반환 (단일 값)
    return result
# 점수들에 대한 정규화 함수 정의
def scale_group(group):
    scaler = MinMaxScaler()
    group[['project_fit']] = scaler.fit_transform(group[['project_fit']])
    return group

def embedding_project(db: Session, company_id:int, project_id:int):
    # 데이터 프레임으로 변경
    projects = get_past_projects_by_company(db, company_id)
    data = pd.DataFrame([{
        "past_project_id": project.past_project_id,
        "employee_id": project.employee_id,
        "role":  get_employee_role_by_id(db, project.employee_id).value,
        "project_name": project.project_name,
        "description": project.description
    } for project in projects])
    # 프로젝트임베딩 벡터 생성
    project_embeddings=[]
    for i in range(len(data)):
        upstage_embedding_vector=client.embeddings.create(
            input=data['description'][i],
            model="solar-embedding-1-large-query"
        )

        project_embeddings.append(upstage_embedding_vector.data[0].embedding)
    
    # 진행할 프로젝트   
    reference_vector=client.embeddings.create(
        input=get_objective_by_project_id(db, project_id),
        model="solar-embedding-1-large-query"
    )
    project_embeddings.append(reference_vector.data[0].embedding)
    
    # 차원 축소하기
    pca = PCA(n_components=100)

    project_reduced_embeddings = pca.fit_transform(project_embeddings)
    project_reduced_embeddings_list = project_reduced_embeddings.tolist()
    #project_reduced_embeddings_list_str = [str(inner_list) for inner_list in project_reduced_embeddings_list]
    # 데이터프레임에 진행할 프로젝트 제외하고 나머지 임베딩값 저장
    data['project_embedding']=project_reduced_embeddings_list[0:len(project_reduced_embeddings_list)-1]
    # 진행할 프로젝트 임베딩 값
    reference_reduced_embedding_vector=project_reduced_embeddings_list[-1]
    #유사도 산출
    data['project_fit'] = data['project_embedding'].apply(lambda x: 1-cosine(x, reference_reduced_embedding_vector))
    # 새로운 테이블에 정규화 점수 저장
    # 정규화 역할(백/프론트.. )별로 적용
    data = data.groupby('role').apply(scale_group).reset_index(drop=True)
    
    db_scaled = get_db_scaled()
        
    try:
        # 기존 프로젝트 적합도
        db_scaled.query(ScaledPastProject).filter(ScaledPastProject.new_project_id == project_id).delete()
        db_scaled.commit()  # 삭제한 내용을 즉시 반영
        
        for index, row in data.iterrows():
            # 새로운객체 생성
            scaled_project = ScaledPastProject(
                past_project_id=row['past_project_id'],
                employee_id=row['employee_id'],
                new_project_id=project_id,
                project_fit_score=row['project_fit'],
                role=row['role'],
            )
            # 새로운 데이터베이스에 삽입
            db_scaled.add(scaled_project)

        db_scaled.commit()  # 커밋을 통해 저장
    except Exception as e:
        db_scaled.rollback()  # 예외가 발생하면 롤백
        raise e  # 예외를 다시 던져서 호출한 곳에서 처리할 수 있도록 함

    finally:
        db_scaled.close()  # 세션 종료

    return "프로젝트 임베딩저장 완료!"
