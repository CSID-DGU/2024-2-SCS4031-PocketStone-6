import pandas as pd
import openai
from dotenv import load_dotenv
import os

load_dotenv()
openai.api_key = os.getenv('OPENAI_API_KEY')

def generate_personality(num_people=100):
    personalities = []
    for i in range(num_people):
        prompt = """
Generate a brief personality description for a fictional person in 2-3 sentences. 
The personality should describe their work preferences, collaboration style, adaptability, and approach to feedback.
        """
        
        response = openai.ChatCompletion.create(
            model="gpt-3.5-turbo",
            messages=[{"role": "user", "content": prompt}],
            temperature=1,
        )
        
        personality = response.choices[0].message['content'].strip()
        personalities.append(personality)
    
    return personalities

personality_list = generate_personality()

df = pd.DataFrame(personality_list, columns=["Personality"])

df.to_csv("personality_data.csv", index=False)
