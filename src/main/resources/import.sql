INSERT INTO USER (ID, user_Id,password,name,email) VALUES(1, 'koo','123','KOOSANGYOON','koo@naver.com');
INSERT INTO USER (ID, user_Id,password,name,email) VALUES(2, 'will','123','PARKJONGSOO','will@daum.net');

INSERT INTO QUESTION (id, writer_id, title, contents, create_date) VALUES(1, 1, '고민이 있습니다.', '코딩이 너무 어려워요',CURRENT_TIMESTAMP());
INSERT INTO QUESTION (id, writer_id, title, contents, create_date) VALUES(2, 2, '고민 들어주세요..', '너무 피곤해요', CURRENT_TIMESTAMP());
