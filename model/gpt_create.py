import openai
import pandas as pd
import numpy as np
from dotenv import load_dotenv
import os


load_dotenv()
openai.api_key = os.getenv('OPENAI_API_KEY')


# 기술 스택과 각 스택별 인원 수 설정
tech_stack_counts = {
    'backend developer': 50,
    'frontend developer': 20,
    'Ux/Ui designer': 10,
    'product manager': 10,
    'data analyst': 10
}

# GPT API를 사용하여 프로젝트 예제 생성
def generate_project_description(tech_stack):
    examples = [
        {"role": "user", "content": "사용자에게 실시간 금융 데이터를 제공하는 백엔드 시스템을 개발했습니다. 안정적인 API와 고도화된 데이터 처리 기능이 포함되었습니다."},
        {"role": "user", "content": "마케팅 자동화 도구를 구축하여 사용자 행동을 분석하고 맞춤형 이메일 캠페인을 실행하는 시스템을 설계했습니다."},
        {"role": "user", "content": "헬스케어 애플리케이션의 UI/UX를 개선하여 사용자 경험을 향상시켰습니다."},
        {"role": "user", "content": "Machine Learning 알고리즘을 활용하여 과거 주가 움직임과 다양한 외부 요인들을 분석했습니다"}
    ]
    
    prompt = (
        f"Create an example that you would have done as a {tech_stack}."
        "Don't talk about the detailed framework."
        "You should write as if you were writing about a project you worked on when you were writing your CV."
        "Please answer in Korean."
    )

    messages = [
        {"role": "system", "content": "You are a helpful assistant for generating project descriptions"},
    ] + examples + [
        {"role": "user", "content": prompt}
    ]

    response = openai.ChatCompletion.create(
        model="gpt-3.5-turbo",
        messages=messages,
        max_tokens=300,
        temperature=1
    )

    return response['choices'][0]['message']['content']


data = []
for stack, count in tech_stack_counts.items():
    for _ in range(count):
        project = generate_project_description(stack)  
        data.append({'기술 스택': stack, '프로젝트 설명': project})


df = pd.DataFrame(data)

df.to_csv("project_describe.csv")