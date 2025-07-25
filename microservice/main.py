from datetime import timedelta
from fastapi import FastAPI, UploadFile, File, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from minio import Minio

app = FastAPI()

origins = ["*"]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

minio_client = Minio(
    "c5dda1fd8933.ngrok-free.app",
    access_key="minioadmin",
    secret_key="minioadmin",
    secure=True
)

BUCKET_NAME = "defesa-civil"

@app.on_event("startup")
def startup_event():
    found = minio_client.bucket_exists(BUCKET_NAME)
    if not found:
        minio_client.make_bucket(BUCKET_NAME)
    else:
        print(f"Bucket '{BUCKET_NAME}' já existe.")

@app.post("/upload")
async def upload_file(file: UploadFile = File(...)):
    try:
        minio_client.put_object(
            BUCKET_NAME,
            file.filename,
            file.file,
            length=-1,
            part_size=10*1024*1024,
            content_type=file.content_type
        )
        return {"file_name": file.filename}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
    
@app.get("/view/{file_name}")
async def get_file_url(file_name: str):
    try:
        presigned_url = minio_client.presigned_get_object(
            BUCKET_NAME,
            file_name,
            expires=timedelta(hours=1),
        )
        return {"url": presigned_url}
    except Exception as e:
        raise HTTPException(status_code=404, detail=f"Arquivo não encontrado.: {e}")