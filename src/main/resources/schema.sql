-- 공용 테이블 (회원) 생성



-- 사이드 프로젝트 테이블 생성
CREATE TABLE SIDE_PROJECT_POST (
   seq INT NOT NULL,
   title VARCHAR(50) NOT NULL,
   contents VARCHAR(20) NOT NULL,
   create_dt DATE
);

CREATE SEQUENCE SIDE_PROJECT_POST_SEQ;