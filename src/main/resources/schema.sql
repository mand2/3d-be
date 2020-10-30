-- 사이드 프로젝트 테이블 생성
CREATE TABLE SIDE_PROJECT_POST (
   seq INT AUTO_INCREMENT NOT NULL,
   leader VARCHAR(20) NOT NULL,
   meeting VARCHAR(10) NOT NULL,
   location VARCHAR(30) NOT NULL,
   status VARCHAR(10) DEFAULT 'Recruiting' NOT NULL,
   mem_capa INT DEFAULT 0,
   mem_total_capa INT NOT NULL,
   title VARCHAR(100) NOT NULL,
   contents VARCHAR(500) NOT NULL,
   delete_yn VARCHAR(2) DEFAULT 'N' NOT NULL,
   create_dt DATE DEFAULT SYSDATE
);

-- 사이드 프로젝트 모집분야 테이블 생성
CREATE TABLE SIDE_PROJECT_REC_AREA (
   seq INT AUTO_INCREMENT NOT NULL,
   post_seq INT NOT NULL,
   area VARCHAR(20) NOT NULL,
   fixed_capa INT DEFAULT 0,
   max_capa INT NOT NULL,
   finish_yn VARCHAR(2) DEFAULT 'N'
);

-- 사이드 프로젝트 지원현황 테이블 생성
CREATE TABLE SIDE_PROJECT_APPLY (
   seq INT AUTO_INCREMENT NOT NULL,
   rec_area_seq INT NOT NULL,
   post_seq INT NOT NULL,
   mem_id VARCHAR(20) NOT NULL,
   rec_area VARCHAR(20) NOT NULL,
   message VARCHAR(100),
   apply_stat VARCHAR(10) NOT NULL DEFAULT 'Waiting',
   create_dt DATE DEFAULT SYSDATE,
   update_dt DATE,
   cancel_yn VARCHAR(2) DEFAULT 'N' NOT NULL
);
