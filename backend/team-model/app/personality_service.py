from sqlalchemy.orm import Session
from sqlalchemy import select
from models import Employee, ScaledEmployee
import pandas as pd
from database import get_db_scaled
from dotenv import load_dotenv
import os
from openai import OpenAI
import openai
from sklearn.decomposition import PCA

load_dotenv()
openai.api_key = os.getenv('OPENAI_API_KEY')

client = OpenAI(
    api_key=os.getenv("UPSTAGE_API_KEY"),
    base_url="https://api.upstage.ai/v1/solar"
)

# 데이터베이스에서 특정 회사의 Employee의 성향 데이터 가져오는 함수
def get_personality_data_by_company(db: Session, company_id: int):
    query = select(
        Employee.employee_id,
        Employee.company_id,
        Employee.personal_type
    ).where(Employee.company_id == company_id)
    
    result = db.execute(query).all()
    return result

# 임베딩 함수
def embedding_employee_personality(db: Session, company_id:int):
    # 데이터 프레임으로 변경
    employees = get_personality_data_by_company(db, company_id)
    data = pd.DataFrame([{
        "employee_id": employee.employee_id,
        "company_id": employee.company_id,
        "personal_type": employee.personal_type,
    } for employee in employees])
    # 성향 임베딩 벡터 생성
    personality_embeddings=[]
    for i in range(len(data)):
        upstage_embedding_vector=client.embeddings.create(
            input=data['personal_type'][i],
            model="solar-embedding-1-large-query"
        )

        personality_embeddings.append(upstage_embedding_vector.data[0].embedding)

    # 차원 축소하기
    pca = PCA(n_components=100)

    personality_reduced_embeddings = pca.fit_transform(personality_embeddings)
    personality_reduced_embeddings_list = personality_reduced_embeddings.tolist()
    personality_reduced_embeddings_list_str = [str(inner_list) for inner_list in personality_reduced_embeddings_list]
    # 데이터프레임에 추가
    data['personality_embedding']=personality_reduced_embeddings_list_str
    
    # 세션가져오기
    db_scaled = get_db_scaled()  
        
    try:
        for index, row in data.iterrows():
            # 사원에 해당하는 기존 레코드를 찾아서 업데이트
            employee_record = db_scaled.query(ScaledEmployee).filter(
                ScaledEmployee.employee_id == row['employee_id'],
                ScaledEmployee.company_id == row['company_id']
            ).first()
            
            if employee_record:
                # 기존 레코드에 임베딩 값 업데이트
                employee_record.personality_embedding = row['personality_embedding']
                db_scaled.commit()  # 변경사항 저장
                
        db_scaled.commit()  # 커밋을 통해 저장
    except Exception as e:
        db_scaled.rollback()  # 예외가 발생하면 롤백
        raise e  # 예외를 다시 던져서 호출한 곳에서 처리할 수 있도록 함

    finally:
        db_scaled.close()  # 세션 종료

    return "성향 임베딩 저장 완료!"