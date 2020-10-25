/*system code 세팅*/
INSERT INTO SYSTEM_CODE(group_code, group_desc, code_seq, code_desc, enable) VALUES ('SS', '스터디 모집/지원', 'SS10', '대기중', true);
INSERT INTO SYSTEM_CODE(group_code, group_desc, code_seq, code_desc, enable) VALUES ('SS', '스터디 모집/지원', 'SS20', '참여', true);
INSERT INTO SYSTEM_CODE(group_code, group_desc, code_seq, code_desc, enable) VALUES ('SS', '스터디 모집/지원', 'SS30', '거절됨', true);
INSERT INTO SYSTEM_CODE(group_code, group_desc, code_seq, code_desc, enable) VALUES ('SS', '스터디 모집/지원', 'SS40', '지원취소', true);
INSERT INTO SYSTEM_CODE(group_code, group_desc, code_seq, code_desc, enable) VALUES ('SS', '스터디 모집/지원', 'SSP1', '모집중', true);
INSERT INTO SYSTEM_CODE(group_code, group_desc, code_seq, code_desc, enable) VALUES ('SS', '스터디 모집/지원', 'SSP2','모집완료', true);

INSERT INTO SYSTEM_CODE(group_code, group_desc, code_seq, code_desc, enable) VALUES ('SP', '스터디 장소 코드', 'SP10', '서울/강남', true);
INSERT INTO SYSTEM_CODE(group_code, group_desc, code_seq, code_desc, enable) VALUES ('SP', '스터디 장소 코드', 'SP11', '서울/건대', true);
INSERT INTO SYSTEM_CODE(group_code, group_desc, code_seq, code_desc, enable) VALUES ('SP', '스터디 장소 코드', 'SP12', '서울/신촌홍대', true);
INSERT INTO SYSTEM_CODE(group_code, group_desc, code_seq, code_desc, enable) VALUES ('SP', '스터디 장소 코드', 'SP13', '서울/여의도', true);
INSERT INTO SYSTEM_CODE(group_code, group_desc, code_seq, code_desc, enable) VALUES ('SP', '스터디 장소 코드', 'SP20', '경기/판교', true);
INSERT INTO SYSTEM_CODE(group_code, group_desc, code_seq, code_desc, enable) VALUES ('SP', '스터디 장소 코드', 'SP21', '경기/수원', true);
INSERT INTO SYSTEM_CODE(group_code, group_desc, code_seq, code_desc, enable) VALUES ('SP', '스터디 장소 코드', 'SP99', '온라인', true);

/*users 세팅*/
INSERT INTO USERS(seq, USER_ID, email, name) values ( null, '125184', 'test@test.com', 'tester');
INSERT INTO USERS(seq, USER_ID, email, name) values ( null, '125185', 'test2@test.com', 'tester2');
INSERT INTO USERS(seq, USER_ID, email, name, CREATED_AT) values ( null, '125185', 'test2@test.com', 'tester2', curtime());
INSERT INTO USERS(seq, USER_ID, email, name, CREATED_AT) values ( null, '125185', 'test2@test.com', 'tester2', curtime());
INSERT INTO USERS(seq, USER_ID, email, name, CREATED_AT) values ( null, '125185', 'test2@test.com', 'tester2', curtime());

-- DELETE from USERS WHERE USER_ID > 0;

/*study_posts 세팅*/
INSERT INTO STUDY_POSTS(seq, title, content, WRITER, MEMBER_NUMBER, PLACE_SEQ, SUBJECT_SEQ)
values (null, '스프링스터디 모집', '모집해요~!', 1, 3, 'SP10', '스프링');
INSERT INTO STUDY_POSTS(seq, title, content, WRITER, MEMBER_NUMBER, PLACE_SEQ, SUBJECT_SEQ)
values (null, '스프링스터디 모집2', '모집해요~!22', 1, 3, 'SP10', 'Java');

INSERT INTO STUDY_POSTS(seq, title, content, WRITER, MEMBER_NUMBER, PLACE_SEQ, SUBJECT_SEQ)
values (null, '리액트스터디 모집', '모집해요~!', 3, 5, 'SP10', '리액트');
INSERT INTO STUDY_POSTS(seq, title, content, WRITER, MEMBER_NUMBER, PLACE_SEQ, SUBJECT_SEQ)
values (null, '머신러닝스터디 모집', '모집해요~!22', 2, 4, 'SP10', 'AI');


/*STUDY_APPLIES 스터디 신청*/
INSERT INTO STUDY_APPLIES(post_seq,apply_user,content,apply_status)
values (1, 2, '신청합니다', 'SS10'), (1, 3, '신청', 'SS10'), (1, 4, '열심히하겠습니다', 'SS20');
INSERT INTO STUDY_APPLIES(post_seq,apply_user,content,apply_status)
values (3, 2, '신청합니다', 'SS10'), (3, 3, '신청', 'SS10'), (3, 4, '열심히하겠습니다', 'SS20');
INSERT INTO STUDY_APPLIES(post_seq,apply_user,content,apply_status)
values (4, 2, '신청합니다', 'SS10'), (4, 3, '신청', 'SS10'), (4, 4, '열심히하겠습니다', 'SS20');


SELECT p.SEQ,
       p.TITLE,
       p.CONTENT,
       u.SEQ  as writer_id,
       u.NAME as writer_name,
       p.MEMBER_NUMBER,
       p.PLACE_SEQ,
       p.STATUS_SEQ,
       p.SUBJECT_SEQ,
       p.CREATED_AT
from STUDY_POSTS p
inner join USERS u on u.SEQ = p.WRITER
where p.DELETE_FLAG = false
and p.TITLE like '%%'
and p.PLACE_SEQ = 'SP10'
-- and p.SUBJECT_SEQ = '스프링'
-- limit 1, 3
-- limit
-- offset
;

-- 모집글자세히보기
SELECT p.SEQ,
       p.TITLE,
       p.CONTENT,
       u.SEQ  as writer_id,
       u.NAME as writer_name,
       p.MEMBER_NUMBER,
       p.STATUS_SEQ,
       p.PLACE_SEQ,
       p.SUBJECT_SEQ,
       p.CREATED_AT
from STUDY_POSTS p
         inner join USERS u on u.SEQ = p.WRITER
where p.SEQ = 1
;

-- 해당 스터디의 appliers list
SELECT
    a.SEQ as apply_seq,
    a.APPLY_USER as apply_user,
    a.APPLY_STATUS as apply_status
from STUDY_APPLIES a
where a.POST_SEQ = 2 ;



-- 모집현황+지원현황 카운트
SELECT
   p.MEMBER_NUMBER,
    sum(case when a.APPLY_STATUS = 'SS10' then true else false end) as SS10 ,
    sum(case when a.APPLY_STATUS = 'SS20' then true else false end) as SS20 ,
    sum(case when a.APPLY_STATUS = 'SS30' then true else false end) as SS30 ,
    sum(case when a.APPLY_STATUS = 'SS40' then true else false end) as SS40
from STUDY_POSTS p
-- inner join USERS u on u.SEQ = p.WRITER
left outer join STUDY_APPLIES a on a.POST_SEQ = p.SEQ
where p.SEQ = 2;


