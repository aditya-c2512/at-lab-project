from fastapi import Request, FastAPI
import numpy as np
from pydantic import BaseModel

from fastapi.middleware.cors import CORSMiddleware

   
app = FastAPI()

# origins = ["*"]

# app.add_middleware(
#     CORSMiddleware,
#     allow_origins=origins,
#     allow_credentials=True,
#     allow_methods=["*"],
#     allow_headers=["*"])

class ID(BaseModel):
    ID: str

import firebase_admin
from firebase_admin import credentials
from firebase_admin import db

# Initialize Firebase credentials
# cred = credentials.Certificate('campuslink-db-firebase-adminsdk-gp3ha-479182eab8.json')
# firebase_admin.initialize_app(cred, {
#     'databaseURL': 'https://campuslink-db-default-rtdb.asia-southeast1.firebasedatabase.app/'
# })

# ref = db.reference('collab')

# # Get the document snapshot by its ID

from transformers import pipeline
generator = pipeline(
    "text-generation",
    model='postbot/emailgen-pythia-410m-deduped',
 # explicitly download model weights
)




@app.get("/")
def read_root():
    return {"Hello": "World"}


@app.post("/generate_email/")
def getmail(ID: ID):
    text = get_email(ID)
    return text

def generate_email(sender_name, project, sender_skills,experience):
    input_text = f"Hi, I hope this email finds you well. My name is {sender_name}, and I came across your work on {project}, and I would love to work under you on this project. I have a keen interest in this area and believe that my skills in {sender_skills} would be an asset to your team. I have {experience}, thus I believe"
    return input_text

def get_email(ID):
    # document_id = '2'
    snapshot = ref.child(ID).get()
    prompt=generate_email(snapshot['name'],snapshot['project'],', '.join(snapshot['skills']),snapshot['relevant_work'])

    result = generator(prompt,)  # generate
    mail=print(result[0]["generated_text"])

    return mail


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)    