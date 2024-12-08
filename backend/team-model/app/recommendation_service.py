from sqlalchemy.orm import Session
import ast
import numpy as np
from itertools import combinations
from models import Skill, Employee, EmployeeSkill, Role,ScaledEmployee,ScaledPastProject, Position, ProjectCharter,PositionSkill
import pandas as pd
from scipy.spatial.distance import cosine
from typing import List



def get_position_num(db: Session, project_id: int) -> dict:
    # 모든 Role 키를 포함하는 기본 구조
    role_counts = {
        "BACKEND_DEVELOPER": 0,
        "FRONTEND_DEVELOPER": 0,
        "UI_UX_DESIGNER": 0,
        "DATA_ANALYST": 0,
        "PRODUCT_MANAGER": 0,
    }

    # 쿼리 실행
    results = (
        db.query(Position.position_name, Position.position_count)
        .join(ProjectCharter, ProjectCharter.id == Position.project_charter_id)
        .filter(ProjectCharter.project_id == project_id)
        .all()
    )

    # 결과를 반복하여 role_counts에 값 저장
    for position_name, position_count in results:
        role_key = position_name.name  # Enum의 이름 가져오기
        if role_key in role_counts:  # 해당 role이 존재하면
            role_counts[role_key] = position_count  # 값 업데이트
    print(role_counts)
    return role_counts

def get_position_skill(db: Session, project_id: int) -> dict:
    # 모든 Role 키를 포함하는 기본 구조 (기본값은 None)
    position_skills = {
        "BACKEND_DEVELOPER": None,
        "FRONTEND_DEVELOPER": None,
        "UI_UX_DESIGNER": None,
        "DATA_ANALYST": None,
        "PRODUCT_MANAGER": None,
    }
    # 쿼리 실행
    results = (
        db.query(Position.position_name, PositionSkill.skill)
            .join(ProjectCharter, ProjectCharter.id == Position.project_charter_id)  # ProjectCharter와 Position 조인
            .join(PositionSkill, PositionSkill.position_id == Position.id)  # Position과 PositionSkill 조인
            .filter(ProjectCharter.project_id == project_id)  # 프로젝트 ID로 필터링
            .all()
    )
    # 결과를 반복하여 position_skills에 값 저장
    for position_name, skill in results:
        role_key = position_name.name  # Enum의 이름 가져오기
        if role_key in position_skills:  # 해당 role이 존재하면
            position_skills[role_key] = skill  # 스킬 업데이트

    return position_skills

# 임의 가중치
def get_weight_type(weight_type: str = "basic"):
    if weight_type == "basic":
        weight_stack = 0.35
        weight_cosine = 0.28
        weight_personality = 0.1
        weight_kpi = 0.07
        weight_peer = 0.2
    elif weight_type == "skill":
        weight_stack = 0.45
        weight_cosine = 0.3
        weight_personality = 0.05
        weight_kpi = 0.05
        weight_peer = 0.15
    elif weight_type == "domain":
        weight_stack = 0.25
        weight_cosine = 0.35
        weight_personality = 0.15
        weight_kpi = 0.05
        weight_peer = 0.2
    elif weight_type == "personality":
        weight_stack = 0.2
        weight_cosine = 0.1
        weight_personality = 0.4
        weight_kpi = 0.1
        weight_peer = 0.2
    else:
        # 기본값 반환
        weight_stack = 0.35
        weight_cosine = 0.28
        weight_personality = 0.1
        weight_kpi = 0.07
        weight_peer = 0.2
    
    return {
        "weight_stack": weight_stack,
        "weight_cosine": weight_cosine,
        "weight_personality": weight_personality,
        "weight_kpi": weight_kpi,
        "weight_peer": weight_peer,
    }

# 회사 아이디랑 스킬, 역할로 사원조회
def get_employee_ids_by_company_and_skill(db: Session, company_id: int, skill: Skill, role: Role) -> list[int]:
    employees = (
        db.query(Employee.employee_id)
        .join(EmployeeSkill, Employee.employee_id == EmployeeSkill.employee_id)
        .filter(
            Employee.company_id == company_id,
            Employee.role == role,
            EmployeeSkill.skill == skill
        )
        .with_entities(Employee.employee_id)
        .all()
    )
    return [employee_id[0] for employee_id in employees]  # 튜플에서 값 추출


    
def filter_team_by_role_and_skill(db: Session, company_id: int, project_id:int):
    conditions = get_position_skill(db, project_id=project_id)
    # 역할별 팀 구성
    back_end = get_employee_ids_by_company_and_skill(
        db, company_id=company_id, skill=conditions['BACKEND_DEVELOPER'], role=Role.BACKEND_DEVELOPER
    )
    front_end = get_employee_ids_by_company_and_skill(
        db, company_id=company_id, skill=conditions['FRONTEND_DEVELOPER'], role=Role.FRONTEND_DEVELOPER
    )
    design = get_employee_ids_by_company_and_skill(
        db, company_id=company_id, skill=conditions['UI_UX_DESIGNER'], role=Role.UI_UX_DESIGNER
    )
    data_analyst = get_employee_ids_by_company_and_skill(
        db, company_id=company_id, skill=conditions['DATA_ANALYST'], role=Role.DATA_ANALYST
    )

    pm = get_employee_ids_by_company_and_skill(
        db, company_id=company_id, skill=conditions['PRODUCT_MANAGER'], role=Role.PRODUCT_MANAGER
    )
    
    result = {
        "BACKEND_DEVELOPER": back_end,
        "FRONTEND_DEVELOPER": front_end,
        "UI_UX_DESIGNER": design,
        "DATA_ANALYST": data_analyst,
        "PRODUCT_MANAGER": pm,
    }
    #print("=========================================================")
    #print(conditions)
    #print(result)
    return result
    
def get_scaled_employees_by_team(scaled_db: Session, employee_ids: list[int]):
    # 주어진 팀 인덱스(employee_id)로 ScaledEmployee 테이블에서 해당 직원들 정보를 조회
    team_data = scaled_db.query(ScaledEmployee).filter(ScaledEmployee.employee_id.in_(employee_ids)).all()
    return team_data   

#해당 프로젝트에 대한 적합도 꺼내오기, 없으면 0, 많으면 맥스
def get_project_fit_score(scaled_db: Session, employee_id:int, project_id:int):
    # 해당 employee_id와 new_project_id에 맞는 프로젝트 적합도들을 모두 조회
    project_fits = scaled_db.query(ScaledPastProject).filter(
        ScaledPastProject.employee_id == employee_id,
        ScaledPastProject.new_project_id == project_id
    ).all()
    
    # 적합도가 없으면 0을 반환, 여러개 있으면 최대값을 반환
    if project_fits:
        return max(project_fit.project_fit_score for project_fit in project_fits)
    else:
        return 0
    

def recommend_team(memberIds:List[int],db: Session, company_id: int, project_id: int,weightType:str,scaled_db:Session):
    position_num = get_position_num(db, project_id=project_id)
    
    filter_team = filter_team_by_role_and_skill(db,company_id=company_id,project_id=project_id)
     # `memberIds`에 포함된 직원만 필터링
    filter_team = {
        role: [employee_id for employee_id in employee_ids if employee_id in memberIds]
        for role, employee_ids in filter_team.items()
    }
    #print(filter_team)

     # 모든 직원 ID 추출
    all_employee_ids = []
    for role, employee_ids in filter_team.items():
        all_employee_ids.extend(employee_ids)
    
    
    # Scaled 데이터 가져오기
    scaled_employees = get_scaled_employees_by_team(scaled_db, employee_ids=all_employee_ids)
    
    # 데이터프레임 변환
    team_df = pd.DataFrame([{
        'employee_id': employee.employee_id,
        'skill_score': employee.skill_score,
        'kpi_score': employee.kpi_score,
        'peer_evaluation_score': employee.peer_evaluation_score,
        'personality_embedding' : employee.personality_embedding
        
    } for employee in scaled_employees])
    team_df['project_fit_score'] = team_df['employee_id'].apply(
                            lambda emp_id: get_project_fit_score(scaled_db, emp_id, project_id)
                        )
    #string 형태로 저장되어 있던 벡터값 리스트로 복원
    team_df['personality_embedding'] = [ast.literal_eval(item) for item in team_df['personality_embedding']]
    #print(team_df)
    final_scores = []
    for back_team in combinations(filter_team['BACKEND_DEVELOPER'], position_num['BACKEND_DEVELOPER']): #백
        for front_team in combinations(filter_team['FRONTEND_DEVELOPER'],  position_num['FRONTEND_DEVELOPER']): #프론트
            for design_team in combinations(filter_team['UI_UX_DESIGNER'], position_num['UI_UX_DESIGNER']):  # 디자인
                for pm_team in combinations(filter_team['PRODUCT_MANAGER'], position_num['PRODUCT_MANAGER']):  # PM
                    for data_team in combinations(filter_team['DATA_ANALYST'], position_num['DATA_ANALYST']):  # 데이터
                        
                        team_indices = list(back_team) + list(front_team) + list(design_team) + list(pm_team) + list(data_team)
                        #team_data = get_scaled_employees_by_team(scaled_db,team_indices=team_indices)
                        team_data = team_df[team_df['employee_id'].isin(team_indices)]
                        avg_stack_score = team_data['skill_score'].mean()
                        avg_kpi_score = team_data['kpi_score'].mean()
                        avg_peer_score = team_data['peer_evaluation_score'].mean()
                        avg_project_score = team_data['project_fit_score'].mean()
                        
                        personality_vectors = np.array(team_data['personality_embedding'].tolist()) # 성향벡터가져오기
                        personality_similarity = []
                        for i, j in combinations(range(len(personality_vectors)), 2):
                            similarity = 1 - cosine(personality_vectors[i], personality_vectors[j])
                            personality_similarity.append(similarity)
                        avg_personality_similarity = np.mean(personality_similarity)
                        final_scores.append((team_indices, avg_stack_score, avg_project_score, 
                                        avg_personality_similarity, avg_kpi_score, avg_peer_score))
    final_scores_df = pd.DataFrame(final_scores, columns=['팀원 인덱스', '기술점수', 
                                                        '프로젝트 적합도', '평균 성향 유사도', 
                                                        'KPI 평가점수', '동료 평가'])
    #####팀별로 성향 유사도 스케일링하기###########################
    min_similarity = final_scores_df['평균 성향 유사도'].min()
    max_similarity = final_scores_df['평균 성향 유사도'].max()
    final_scores_df['평균 성향 유사도 (스케일링)'] = (final_scores_df['평균 성향 유사도'] - min_similarity) / (max_similarity - min_similarity)
    #################가중치 부여
    weight = get_weight_type(weightType)
    final_scores_df['최종 점수'] = (
        final_scores_df['기술점수'] * weight['weight_stack'] +
        final_scores_df['프로젝트 적합도'] * weight['weight_cosine'] +
        final_scores_df['평균 성향 유사도 (스케일링)'] * weight['weight_personality'] +
        final_scores_df['KPI 평가점수'] * weight['weight_kpi'] +
        final_scores_df['동료 평가'] * weight['weight_peer']
    )

    final_scores_df.sort_values(by='최종 점수', ascending=False, inplace=True)
    
    #return final_scores_df.head(8)
    # 팀별로 결과를 묶어서 반환
    grouped_results = []
    for _, row in final_scores_df.head(3).iterrows():
        final_team_data = {
            "team_indices": row["팀원 인덱스"],  # 팀원 인덱스
            "skill_score": row["기술점수"],  # 기술 점수
            "project_fit_score": row["프로젝트 적합도"],  # 프로젝트 적합도
            "avg_personality_similarity": row["평균 성향 유사도"],  # 평균 성향 유사도
            "scaled_personality_similarity": row["평균 성향 유사도 (스케일링)"],  # 스케일링된 성향 유사도
            "kpi_score": row["KPI 평가점수"],  # KPI 평가 점수
            "peer_evaluation_score": row["동료 평가"],  # 동료 평가
            "final_score": row["최종 점수"],  # 최종 점수
        }
        grouped_results.append(final_team_data)
    #print(grouped_results)
    # JSON 형태로 반환
    return {"teams": grouped_results}
