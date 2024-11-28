import os
from openai import OpenAI
import openai
import pandas as pd
from dotenv import load_dotenv
import numpy as np
from sklearn.decomposition import PCA
import ast

load_dotenv()
openai.api_key = os.getenv('OPENAI_API_KEY')

client = OpenAI(
    api_key=os.getenv("UPSTAGE_API_KEY"),
    base_url="https://api.upstage.ai/v1/solar"
)

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

print(project_reduced_embeddings_list_str)