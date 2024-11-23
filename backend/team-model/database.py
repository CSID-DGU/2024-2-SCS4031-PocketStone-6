from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

# MySQL 데이터베이스 URL을 설정합니다.
DATABASE_URL = "mysql+pymysql://root:0000@localhost:3306/team_sync"

# SQLAlchemy 엔진을 생성합니다.
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