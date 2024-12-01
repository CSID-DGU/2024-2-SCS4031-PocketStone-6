from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

DATABASE_URL = "mysql+pymysql://root:0000@localhost:3306/team_sync"
# 저장용 데베        
SCALED_DATABASE_URL = "mysql+pymysql://root:0000@localhost:3306/scaled_team_sync"

# SQLAlchemy 엔진을 생성
engine = create_engine(DATABASE_URL)

# 세션 로컬 설정
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

# Base 클래스 생성
Base = declarative_base()

def get_db():
    db = SessionLocal()  # 새로운 세션 인스턴스 생성
    try:
        yield db  # 의존성 주입을 위한 generator 사용
    finally:
        db.close()  # 세션 종료
 
scaled_engine = create_engine(SCALED_DATABASE_URL)
SessionLocalScaled = sessionmaker(autocommit=False, autoflush=False, bind=scaled_engine)
Base_scaled = declarative_base()
def get_db_scaled():
    db = SessionLocalScaled()
    return db