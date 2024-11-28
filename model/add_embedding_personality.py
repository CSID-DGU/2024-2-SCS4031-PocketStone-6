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

data_personality = pd.read_excel('please.xlsx', sheet_name='사원정보')

personality_embeddings=[]

for i in range(len(data_personality)):
    upstage_embedding_vector=client.embeddings.create(
        input=data_personality['개인 성향'][i],
        model="solar-embedding-1-large-query"
    )

    personality_embeddings.append(upstage_embedding_vector.data[0].embedding)

pca = PCA(n_components=150)

personality_reduced_embeddings = pca.fit_transform(personality_embeddings)
personality_reduced_embeddings_list = personality_reduced_embeddings.tolist()

personality_reduced_embeddings_list_str = [str(inner_list) for inner_list in personality_reduced_embeddings_list]

print(personality_reduced_embeddings_list_str)