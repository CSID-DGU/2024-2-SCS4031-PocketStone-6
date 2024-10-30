import openai
import pandas as pd
import numpy as np
from dotenv import load_dotenv
import os
from docx import Document

load_dotenv()
openai.api_key = os.getenv('OPENAI_API_KEY')

tech_stack_counts = {
    'backend developer': 50,
    'frontend developer': 20,
    'Ux/Ui designer': 10,
    'product manager': 10,
    'data analyst': 10
}

def read_word_file(file_path):
    document = Document(file_path)
    return [para.text for para in document.paragraphs if para.text.strip()]

def read_multiple_word_files(file_paths):
    all_text = []
    for file_path in file_paths:
        all_text.extend(read_word_file(file_path))
    return all_text

def generate_project_description(tech_stack, context):
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
        f"Incorporate the following context: {context}"
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

word_file_paths = [
    "C://Users//82102//Desktop//4-2//캡스톤//인사평가서.docx", 
    "C://Users//82102//Desktop//4-2//캡스톤//인사평가서(등급B)_샘플.docx",
    "C://Users//82102//Desktop//4-2//캡스톤//인사평가서(등급C)_샘플.docx"
]

context_lines = read_multiple_word_files(word_file_paths)
context = ' '.join(context_lines)

data = []
for stack, count in tech_stack_counts.items():
    for _ in range(count):
        project = generate_project_description(stack, context)  
        data.append({'기술 스택': stack, '프로젝트 설명': project})

df = pd.DataFrame(data)

df.to_csv("project_describe.csv", index=False)