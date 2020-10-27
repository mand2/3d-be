
-- 시스템 코드 테이블 데이터 삽입
-- INSERT INTO SYSTEM_CODE VALUES (SYSTEM_CODE_SEQ.nextval, 'STAT', )

-- 사이드 프로젝트 글 테스트 데이터
INSERT INTO SIDE_PROJECT_POST(leader, meeting, location,
                              status, mem_total_capa,
                              title, contents, create_dt) VALUES
                              ('testuser', 'ONLINE', '서울 서초구',
                               '모집중', 5, '당근마켓 비슷한 거', '개발해봐요', sysdate);
INSERT INTO SIDE_PROJECT_POST(leader, meeting, location,
                              status, mem_total_capa,
                              title, contents, create_dt) VALUES
('yyyy', 'ONLINE', '서울 강남구',
 '모집중', 5, '커머스 앱', '앱초기 단계입니다', sysdate);
INSERT INTO SIDE_PROJECT_POST(leader, meeting, location,
                              status, mem_total_capa,
                              title, contents, create_dt) VALUES
('deokhoo', 'OFFLINE', '서울 서초구',
 '모집중', 5, '아이돌 덕질 앱', '만들어요', sysdate);
