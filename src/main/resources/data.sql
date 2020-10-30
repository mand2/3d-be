
-- 시스템 코드 테이블 데이터 삽입
-- INSERT INTO SYSTEM_CODE VALUES (SYSTEM_CODE_SEQ.nextval, 'STAT', )

-- 사이드 프로젝트 모집글 테스트 데이터
INSERT INTO SIDE_PROJECT_POST(leader, meeting, location,
                              status, mem_total_capa,
                              title, contents, create_dt) VALUES
                              ('musicman', 'ONLINE', '서울 서초구',
                               '모집중', 3, '음악 공유앱 만드실 분?', '사람들은 모르는 나만 아는 노래들 공유하는 어플 만들기', sysdate);
INSERT INTO SIDE_PROJECT_POST(leader, meeting, location,
                              status, mem_total_capa,
                              title, contents, create_dt) VALUES
('zombiegirl', 'ONLINE', '서울 강남구',
 '모집중', 4, '도트 좀비게임 만들어요~', '도트로 표현되는 작은 좀비게임 만들어보고 싶습니다ㅎㅎ', sysdate);
INSERT INTO SIDE_PROJECT_POST(leader, meeting, location,
                              status, mem_total_capa,
                              title, contents, create_dt) VALUES
('plzrun', 'OFFLINE', '제주도',
 '모집중', 5, '이 어플 사용자 0순위가 저입니다ㅠㅠ 운동 좀 하라고 쪼는 어플 만들기', '같이 만들어요~', sysdate);
