import numpy as np
import pandas as pd
import os
from openai import OpenAI
import openai
from dotenv import load_dotenv
from sklearn.preprocessing import MinMaxScaler
from sklearn.decomposition import PCA
import ast
from itertools import combinations

# 엑셀 데이터 불러오기
data1 = pd.read_excel('please.xlsx', sheet_name='사원정보')
data2 = pd.read_excel('please.xlsx', sheet_name='프로젝트 내역')

# 사원 정보와 프로젝트 내역 합치기
test_data=pd.merge(data1[['사원번호','분야','사용기술','기술점수','KPI평가점수','동료평가','개인 성향']], data2[['사원번호', '프로젝트 설명']], on='사원번호', how='inner')

# 개인 성향 및 프로젝트 임베딩하기
load_dotenv()
openai.api_key = os.getenv('OPENAI_API_KEY')

client = OpenAI(
    api_key=os.getenv("UPSTAGE_API_KEY"),
    base_url="https://api.upstage.ai/v1/solar"
)

data_personality = pd.read_excel('please.xlsx', sheet_name='사원정보')

personality_embeddings=[]

for i in range(len(data_personality)):
    upstage_embedding_vector=client.embeddings.create(
        input=data_personality['개인 성향'][i],
        model="solar-embedding-1-large-query"
    )

    personality_embeddings.append(upstage_embedding_vector.data[0].embedding)

pca = PCA(n_components=100)

personality_reduced_embeddings = pca.fit_transform(personality_embeddings)
personality_reduced_embeddings_list = personality_reduced_embeddings.tolist()
personality_reduced_embeddings_list_str = [str(inner_list) for inner_list in personality_reduced_embeddings_list]
test_data['개인 성향 임베딩 벡터']=personality_reduced_embeddings_list_str

data_project = pd.read_excel('please.xlsx', sheet_name='프로젝트 내역')


project_embeddings=[]


for i in range(len(data_project)):
    upstage_embedding_vector=client.embeddings.create(
        input=data_project['프로젝트 설명'][i],
        model="solar-embedding-1-large-query"
    )

    project_embeddings.append(upstage_embedding_vector.data[0].embedding)

reference_vector=client.embeddings.create(
        input="진행할 프로젝트 설명이 들어갑니다.",
        model="solar-embedding-1-large-query"
    )

project_embeddings.append(reference_vector.data[0].embedding)

pca = PCA(n_components=100)

project_reduced_embeddings = pca.fit_transform(project_embeddings)
project_reduced_embeddings_list = project_reduced_embeddings.tolist()

project_reduced_embeddings_list_str = [str(inner_list) for inner_list in project_reduced_embeddings_list]

test_data['프로젝트 설명 임베딩 벡터']=project_reduced_embeddings_list_str[0:len(project_reduced_embeddings_list_str)-1]

reference_reduced_embedding_vector=project_reduced_embeddings_list_str[-1]

# string 형태로 저장되어 있던 벡터값 리스트로 복원
test_data['개인 성향 임베딩 벡터'] = [ast.literal_eval(item) for item in test_data['개인 성향 임베딩 벡터']]
test_data['프로젝트 설명 임베딩 벡터'] = [ast.literal_eval(item) for item in test_data['프로젝트 설명 임베딩 벡터']]
reference_reduced_embedding_vector=ast.literal_eval(reference_reduced_embedding_vector)

# 사원들의 기존 프로젝트와 진행할 프로젝트 유사도를 계산해 프로젝트 적합도 열 생성
from scipy.spatial.distance import cosine
test_data['프로젝트 적합도'] = test_data['프로젝트 설명 임베딩 벡터'].apply(lambda x: 1-cosine(x, reference_reduced_embedding_vector))

# 점수들에 대한 scaling
def scale_group(group):
    scaler = MinMaxScaler()
    group[['기술점수', 'KPI평가점수','동료평가','프로젝트 적합도']] = scaler.fit_transform(group[['기술점수', 'KPI평가점수','동료평가','프로젝트 적합도']])
    return group

test_data = test_data.groupby('분야').apply(scale_group).reset_index(drop=True)

# 스택 조건에 맞게 데이터 걸러내기
conditions = {
    'BE': 'SpringBoot',
    'FE': 'React',
    'DE': 'Adobe XD',
    'DA': 'SAS',
    'PM': '기획'
}

filtered_results = []


for field, tech in conditions.items():
    filtered_data = test_data[(test_data['분야'] == field) & (test_data['사용기술'].str.contains(tech, na=False))]
    filtered_results.append(filtered_data)

filtered_data = pd.concat(filtered_results, ignore_index=True)

# 팀 추천 조합
back_end = filtered_data[filtered_data['분야'] == 'BE']
front_end = filtered_data[filtered_data['분야'] == 'FE']
design = filtered_data[filtered_data['분야'] == 'DE']
pm = filtered_data[filtered_data['분야'] == 'PM']
data = filtered_data[filtered_data['분야'] == 'DA']

final_scores = []

# 팀 구성 인원
back_num = 1
front_num = 0
design_num = 1
pm_num = 1
data_num = 1

# 임의 가중치
weight_stack = 0.2
weight_cosine = 0.2
weight_personality = 0.2
weight_kpi = 0.2
weight_peer = 0.2

for back_team in combinations(back_end.index, back_num):
    for front_team in combinations(front_end.index, front_num):
        for design_team in combinations(design.index, design_num):
            for pm_team in combinations(pm.index, pm_num):
                for data_team in combinations(data.index, data_num):
                    team_indices = list(back_team) + list(front_team) + list(design_team) + list(pm_team) + list(data_team)
                    team_data = test_data.loc[team_indices]

                    avg_stack_score = team_data['기술점수'].mean()
                    avg_cosine_score = team_data['프로젝트 적합도'].mean()
                    avg_kpi_score = team_data['KPI평가점수'].mean()
                    avg_peer_score = team_data['동료평가'].mean()

                    personality_vectors = np.array(team_data['개인 성향 임베딩 벡터'].tolist())
                    personality_similarity = []
                    for i, j in combinations(range(len(personality_vectors)), 2):
                        similarity = 1 - cosine(personality_vectors[i], personality_vectors[j])
                        personality_similarity.append(similarity)
                    avg_personality_similarity = np.mean(personality_similarity)

                    final_scores.append((team_indices, avg_stack_score, avg_cosine_score, 
                                         avg_personality_similarity, avg_kpi_score, avg_peer_score))

final_scores_df = pd.DataFrame(final_scores, columns=['팀원 인덱스', '기술점수', 
                                                       '프로젝트 적합도', '평균 성향 유사도', 
                                                       'KPI평가점수', '동료평가'])

min_similarity = final_scores_df['평균 성향 유사도'].min()
max_similarity = final_scores_df['평균 성향 유사도'].max()
final_scores_df['평균 성향 유사도 (스케일링)'] = (final_scores_df['평균 성향 유사도'] - min_similarity) / (max_similarity - min_similarity)

final_scores_df['최종 점수'] = (
    final_scores_df['기술점수'] * weight_stack +
    final_scores_df['프로젝트 적합도'] * weight_cosine +
    final_scores_df['평균 성향 유사도 (스케일링)'] * weight_personality +
    final_scores_df['KPI평가점수'] * weight_kpi +
    final_scores_df['동료평가'] * weight_peer
)

final_scores_df.sort_values(by='최종 점수', ascending=False, inplace=True)