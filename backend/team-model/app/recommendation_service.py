from sqlalchemy.orm import Session
import ast
import numpy as np
from itertools import combinations
from models import Skill, Employee, EmployeeSkill, Role,ScaledEmployee,ScaledPastProject
import pandas as pd
from scipy.spatial.distance import cosine
# 팀 구성 인원 -가져오기
back_num = 1
front_num = 0
design_num = 1
pm_num = 0
data_num = 1

# 스택 조건에 맞게 데이터 걸러내기 -데베에서 꺼내기기
conditions = {
    'BE': Skill.RUBY,
    'FE': Skill.REACT,
    'DE': Skill.SKETCH,
    'DA': Skill.R
}
# 임의 가중치
weight_stack = 0.2
weight_cosine = 0.2
weight_personality = 0.2
weight_kpi = 0.2
weight_peer = 0.2

# 회사 아이디랑 스킬, 역할로 사원조회
def get_employee_ids_by_company_and_skill(db: Session, company_id: int, skill: Skill, role: Role) -> list[int]:
    if skill is None:
        employees = (
            db.query(Employee.employee_id)
            .filter(
                Employee.company_id == company_id,
                Employee.role == role
            )
            .with_entities(Employee.employee_id)
            .all()
        )
    else:
        # skill이 주어졌을 때만 해당 skill로 필터링
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


    
def filter_team_by_role_and_skill(db: Session, company_id: int):
    # 역할별 팀 구성
    back_end = get_employee_ids_by_company_and_skill(
        db, company_id=company_id, skill=conditions['BE'], role=Role.BACKEND_DEVELOPER
    )
    front_end = get_employee_ids_by_company_and_skill(
        db, company_id=company_id, skill=conditions['FE'], role=Role.FRONTEND_DEVELOPER
    )
    design = get_employee_ids_by_company_and_skill(
        db, company_id=company_id, skill=conditions['DE'], role=Role.UI_UX_DESIGNER
    )
    data_analyst = get_employee_ids_by_company_and_skill(
        db, company_id=company_id, skill=conditions['DA'], role=Role.DATA_ANALYST
    )
    # PM은 기술 조건이 없으므로 별도 처리
    pm = get_employee_ids_by_company_and_skill(
        db, company_id=company_id, skill=None, role=Role.PRODUCT_MANAGER
    )
    
    result = {
        "BACKEND_DEVELOPER": back_end,
        "FRONTEND_DEVELOPER": front_end,
        "UI_UX_DESIGNER": design,
        "DATA_ANALYST": data_analyst,
        "PRODUCT_MANAGER": pm,
    }
    
    return result
    
def get_scaled_employees_by_team(scaled_db: Session, team_indices: list[int]):
    # 주어진 팀 인덱스(employee_id)로 ScaledEmployee 테이블에서 해당 직원들 정보를 조회
    team_data = scaled_db.query(ScaledEmployee).filter(ScaledEmployee.employee_id.in_(team_indices)).all()
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
    
       
def recommend_team(db: Session, company_id: int, project_id: int,scaled_db:Session):
    filter_team = filter_team_by_role_and_skill(db,company_id=company_id)
    # 맨먼스랑 겹치는것만 다시 가져와
    final_scores = []
    for back_team in combinations(filter_team['BACKEND_DEVELOPER'], back_num): #백
        for front_team in combinations(filter_team['FRONTEND_DEVELOPER'], front_num): #프론트
            for design_team in combinations(filter_team['UI_UX_DESIGNER'], design_num):  # 디자인
                for pm_team in combinations(filter_team['PRODUCT_MANAGER'], pm_num):  # PM
                    for data_team in combinations(filter_team['DATA_ANALYST'], data_num):  # 데이터
                        
                        team_indices = list(back_team) + list(front_team) + list(design_team) + list(pm_team) + list(data_team)
                        team_data = get_scaled_employees_by_team(scaled_db,team_indices=team_indices)
                        # 팀 데이터를 DataFrame으로 변환
                        team_df = pd.DataFrame([{
                            'employee_id': emp.employee_id,
                            'skill_score': emp.skill_score,
                            'kpi_score': emp.kpi_score,
                            'peer_evaluation_score': emp.peer_evaluation_score,
                            'personality_embedding' : emp.personality_embedding
                        } for emp in team_data])
                        
                        # 프로젝트 적합도 가져오기
                        team_df['project_fit_score'] = team_df['employee_id'].apply(
                            lambda emp_id: get_project_fit_score(scaled_db, emp_id, project_id)
                        )
                        print(team_df)
                        avg_stack_score = team_df['skill_score'].mean()
                        avg_kpi_score = team_df['kpi_score'].mean()
                        avg_peer_score = team_df['peer_evaluation_score'].mean()
                        avg_project_score = team_df['project_fit_score'].mean()
                        #string 형태로 저장되어 있던 벡터값 리스트로 복원
                        team_df['personality_embedding'] = [ast.literal_eval(item) for item in team_df['personality_embedding']]
                        personality_vectors = np.array(team_df['personality_embedding'].tolist()) # 성향벡터가져오기
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
    final_scores_df['최종 점수'] = (
        final_scores_df['기술점수'] * weight_stack +
        final_scores_df['프로젝트 적합도'] * weight_cosine +
        final_scores_df['평균 성향 유사도 (스케일링)'] * weight_personality +
        final_scores_df['KPI 평가점수'] * weight_kpi +
        final_scores_df['동료 평가'] * weight_peer
    )

    final_scores_df.sort_values(by='최종 점수', ascending=False, inplace=True)
    
    return final_scores_df.head(1)
