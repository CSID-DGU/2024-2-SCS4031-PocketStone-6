from sklearn.preprocessing import MinMaxScaler
from sqlalchemy.orm import Session
from sqlalchemy import select
from models import Employee, ScaledEmployee
import pandas as pd
from database import get_db_scaled

# 점수들에 대한 정규화 함수 정의
def scale_group(group):
    scaler = MinMaxScaler()
    group[['skill_score','kpi_score', 'peer_evaluation_score']] = scaler.fit_transform(group[['skill_score','kpi_score', 'peer_evaluation_score']])
    return group

# 데이터베이스에서 특정 회사의 Employee 데이터를 가져오는 함수
def get_employee_data_by_company(db: Session, company_id: int):
    query = select(
        Employee.employee_id,
        Employee.company_id,
        Employee.skill_score,
        Employee.kpi_score,
        Employee.peer_evaluation_score,
        Employee.role
    ).where(Employee.company_id == company_id)
    
    result = db.execute(query).all()
    return result

# 정규화 후 새로저장하는 함수
def scale_employee_data(db: Session, company_id:int):
    # 데이터프레임으로 변환
    employees = get_employee_data_by_company(db, company_id)
    data = pd.DataFrame([{
        "employee_id": employee.employee_id,
        "company_id": employee.company_id,
        "skill_score": employee.skill_score,
        "kpi_score": employee.kpi_score,
        "peer_evaluation_score": employee.peer_evaluation_score,
        #"personal_type": employee.personal_type,
        "role": employee.role.value  # Role을 value로 변환
    } for employee in employees])

    # 정규화 역할(백/프론트.. )별로 적용
    data = data.groupby('role').apply(scale_group).reset_index(drop=True)
    # 새로운 테이블에 정규화 점수 저장
    
    # 새로운 테이블에 정규화 점수 저장
    db_scaled = get_db_scaled()  
        
    try:
        # 기존 회사 사원데이이 삭제
        db_scaled.query(ScaledEmployee).filter(ScaledEmployee.company_id == company_id).delete()
        db_scaled.commit()  # 삭제한 내용을 즉시 반영
        
        for index, row in data.iterrows():
            # 새로운 ScaledEmployee 객체 생성
            scaled_employee = ScaledEmployee(
                employee_id=row['employee_id'],
                company_id=row['company_id'],
                skill_score=row['skill_score'],
                kpi_score=row['kpi_score'],
                peer_evaluation_score=row['peer_evaluation_score'],
                role=row['role'],
                #personality_embedding=personality_embedding
            )
            # 새로운 데이터베이스에 삽입
            db_scaled.add(scaled_employee)

        db_scaled.commit()  # 커밋을 통해 저장
    except Exception as e:
        db_scaled.rollback()  # 예외가 발생하면 롤백
        raise e  # 예외를 다시 던져서 호출한 곳에서 처리할 수 있도록 함

    finally:
        db_scaled.close()  # 세션 종료

    return "정규화 점수 저장 완료!"