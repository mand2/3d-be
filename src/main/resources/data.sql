
-- 시스템 코드 테이블 데이터 삽입
-- INSERT INTO SYSTEM_CODE VALUES (SYSTEM_CODE_SEQ.nextval, 'STAT', )

-- 사이드 프로젝트 글 테스트 데이터
INSERT INTO SIDE_PROJECT_POST(leader, meeting, location,
                              status, mem_total_capa, mem_info_seq,
                              title, contents, create_dt) VALUES
                              ('testuser', 'ONLINE', '서울 서초구',
                               '모집중', 5, 4, '스프링 스터디', '토비의 스프링 읽읍시다', sysdate);

