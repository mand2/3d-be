CREATE TABLE USERS
(
    seq           bigint      NOT NULL AUTO_INCREMENT,
    user_id       bigint      NOT NULL,
    email         varchar(50) NOT NULL,
    name          varchar(50) NOT NULL,
    created_at    datetime    NULL     DEFAULT CURRENT_TIMESTAMP(),
    last_login_at datetime    NULL,
    login_count   int         NOT NULL DEFAULT 0,
    delete_flag   char(1)     NULL     DEFAULT 'N',
    primary key (seq)
);


CREATE TABLE STUDY_POSTS (
	seq	            bigint	        NOT NULL AUTO_INCREMENT COMMENT '모집글번호/A.I' ,
	title	        varchar(100)	NOT NULL COMMENT '글제목' ,
	content	        varchar(500)	NOT NULL COMMENT '내용' ,
	writer	        bigint	        NOT NULL COMMENT '작성자의 회원번호 USERS.seq' ,
    member_number	int(3)	        NOT NULL default 1 COMMENT '모집인원' ,
    status_seq	    char(4)	        NULL default 'SSP1' COMMENT '상태코드-모집결과 SYSTEM_CODE.code_seq' ,
    place_seq	    char(4)	        NOT NULL COMMENT '장소코드-모집장소 SYSTEM_CODE.code_seq' ,
    subject_seq	    varchar(100)	NOT NULL COMMENT '스터디 주제' ,
	view_count    	int(11)	        NULL DEFAULT 0,
	like_count    	int(11)	        NULL DEFAULT 0,
	comment_count 	int(11)	        NULL DEFAULT 0,
	created_at      datetime	    NULL DEFAULT CURRENT_TIMESTAMP()	COMMENT '작성시각',
	updated_at	    datetime	    NULL            COMMENT '업데이트시각',
	delete_flag	    tinyint(1)	    NULL DEFAULT 0  COMMENT '삭제여부',
    primary key (seq)
);

/*TODO 나중에 subject를 여러개 받을 때 사용함. 지금은 XX */
CREATE TABLE STUDY_POST_TAGS (
    post_seq	    bigint	NOT NULL	COMMENT '모집글번호',
    subject_seq	    char(4)	NOT NULL	COMMENT '주제코드/SYSTEM_CODE.code_seq'
);


CREATE TABLE STUDY_APPLIES (
    seq             bigint	        NOT NULL AUTO_INCREMENT	COMMENT '지원글번호/A.I',
    post_seq	    bigint	        NOT NULL	COMMENT '모집글번호',
    apply_user  	bigint	        NOT NULL	COMMENT '스터디지원자회원번호',
    content	        varchar(500)	NOT NULL	COMMENT '자기소개 등 지원내용',
    apply_status	char(4)	        NOT NULL	COMMENT '지원현황 SYSTEM_CODE.code_seq',
    created_at	    datetime	    NULL	    DEFAULT CURRENT_TIMESTAMP()	COMMENT '작성시각',
    primary key (post_seq, apply_user)
);

CREATE TABLE SYSTEM_CODE (
    group_code	char(2)	NOT NULL	COMMENT '상위코드/카테고리',
	group_desc	varchar(50)	NULL	COMMENT '상위코드 값',
    code_seq	char(4)	NOT NULL	COMMENT '사용 코드',
	code_desc 	varchar(50)	NULL	COMMENT '코드값(모집현황,지원현황, 장소 등)',
	enable	    tinyint(1)	NULL	DEFAULT 1	COMMENT '사용가능여부(0:false 1:true)'
);

