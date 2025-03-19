-- 게임 기록 테이블 생성
CREATE TABLE history (
    gameno NUMBER(10) PRIMARY KEY,
    result VARCHAR2(20) NOT NULL,
    gamedate DATE NOT NULL
);

-- 화이트 기물 이동기록 테이블 생성
CREATE TABLE white (
    gameno NUMBER(10) NOT null, 
    turn NUMBER(10)  DEFAULT 0,
    w_time NUMBER(10)  DEFAULT 900,
    w_pawn1_row NUMBER(10)  DEFAULT 7,
    w_pawn1_col NUMBER(10)  DEFAULT 1,
    w_pawn1_move NUMBER(10) DEFAULT 0,
    w_pawn1_promotion VARCHAR2(20)  DEFAULT 'pawn',
    w_pawn2_row NUMBER(10)  DEFAULT 7,
    w_pawn2_col NUMBER(10)  DEFAULT 2,
    w_pawn2_move NUMBER(10) DEFAULT 0,
    w_pawn2_promotion VARCHAR2(20) DEFAULT 'pawn',
    w_pawn3_row NUMBER(10)  DEFAULT 7,
    w_pawn3_col NUMBER(10)  DEFAULT 3,
    w_pawn3_move NUMBER(10) DEFAULT 0,
    w_pawn3_promotion VARCHAR2(20) DEFAULT 'pawn',
    w_pawn4_row NUMBER(10)  DEFAULT 7,
    w_pawn4_col NUMBER(10)  DEFAULT 4,
    w_pawn4_move NUMBER(10) DEFAULT 0,
    w_pawn4_promotion VARCHAR2(20) DEFAULT 'pawn',
    w_pawn5_row NUMBER(10)  DEFAULT 7,
    w_pawn5_col NUMBER(10)  DEFAULT 5,
    w_pawn5_move NUMBER(10) DEFAULT 0,
    w_pawn5_promotion VARCHAR2(20) DEFAULT 'pawn',
    w_pawn6_row NUMBER(10)  DEFAULT 7,
    w_pawn6_col NUMBER(10)  DEFAULT 6,
    w_pawn6_move NUMBER(10) DEFAULT 0,
    w_pawn6_promotion VARCHAR2(20) DEFAULT 'pawn',
    w_pawn7_row NUMBER(10)  DEFAULT 7,
    w_pawn7_col NUMBER(10)  DEFAULT 7,
    w_pawn7_move NUMBER(10) DEFAULT 0,
    w_pawn7_promotion VARCHAR2(20) DEFAULT 'pawn',
    w_pawn8_row NUMBER(10)  DEFAULT 7,
    w_pawn8_col NUMBER(10)  DEFAULT 8,
    w_pawn8_move NUMBER(10) DEFAULT 0,
    w_pawn8_promotion VARCHAR2(20) DEFAULT 'pawn',
    w_rook1_row NUMBER(10)  DEFAULT 8,
    w_rook1_col NUMBER(10)  DEFAULT 1,
    w_rook1_move NUMBER(10) DEFAULT 0,
    w_rook2_row NUMBER(10)  DEFAULT 8,
    w_rook2_col NUMBER(10)  DEFAULT 8,
    w_rook2_move NUMBER(10) DEFAULT 0,
    w_bishop1_row NUMBER(10)  DEFAULT 8,
    w_bishop1_col NUMBER(10)  DEFAULT 3,
    w_bishop1_move NUMBER(10) DEFAULT 0,
    w_bishop2_row NUMBER(10)  DEFAULT 8,
    w_bishop2_col NUMBER(10)  DEFAULT 6,
    w_bishop2_move NUMBER(10) DEFAULT 0,
    w_knight1_row NUMBER(10)  DEFAULT 8,
    w_knight1_col NUMBER(10)  DEFAULT 2,
    w_knight1_move NUMBER(10) DEFAULT 0,
    w_knight2_row NUMBER(10)  DEFAULT 8,
    w_knight2_col NUMBER(10)  DEFAULT 7,
    w_knight2_move NUMBER(10) DEFAULT 0,
    w_queen_row NUMBER(10)  DEFAULT 8,
    w_queen_col NUMBER(10)  DEFAULT 4,
    w_queen_move NUMBER(10) DEFAULT 0,
    w_king_row NUMBER(10)  DEFAULT 8,
    w_king_col NUMBER(10)  DEFAULT 5,
    w_king_move NUMBER(10) DEFAULT 0,
    FOREIGN KEY (gameno) REFERENCES history(gameno)
);

-- 블랙 기물 이동기록 테이블 생성
CREATE TABLE black (
    gameno NUMBER(10) NOT null,
    turn NUMBER(10)  DEFAULT 0,
    b_time NUMBER(10)  DEFAULT 900,
    b_pawn1_row NUMBER(10)  DEFAULT 2,
    b_pawn1_col NUMBER(10)  DEFAULT 1,
    b_pawn1_move NUMBER(10) DEFAULT 0,
    b_pawn1_promotion VARCHAR2(20)  DEFAULT 'pawn',
    b_pawn2_row NUMBER(10)  DEFAULT 2,
    b_pawn2_col NUMBER(10)  DEFAULT 2,
    b_pawn2_move NUMBER(10) DEFAULT 0,
    b_pawn2_promotion VARCHAR2(20) DEFAULT 'pawn',
    b_pawn3_row NUMBER(10)  DEFAULT 2,
    b_pawn3_col NUMBER(10)  DEFAULT 3,
    b_pawn3_move NUMBER(10) DEFAULT 0,
    b_pawn3_promotion VARCHAR2(20) DEFAULT 'pawn',
    b_pawn4_row NUMBER(10)  DEFAULT 2,
    b_pawn4_col NUMBER(10)  DEFAULT 4,
    b_pawn4_move NUMBER(10) DEFAULT 0,
    b_pawn4_promotion VARCHAR2(20) DEFAULT 'pawn',
    b_pawn5_row NUMBER(10)  DEFAULT 2,
    b_pawn5_col NUMBER(10)  DEFAULT 5,
    b_pawn5_move NUMBER(10) DEFAULT 0,
    b_pawn5_promotion VARCHAR2(20) DEFAULT 'pawn',
    b_pawn6_row NUMBER(10)  DEFAULT 2,
    b_pawn6_col NUMBER(10)  DEFAULT 6,
    b_pawn6_move NUMBER(10) DEFAULT 0,
    b_pawn6_promotion VARCHAR2(20) DEFAULT 'pawn',
    b_pawn7_row NUMBER(10)  DEFAULT 2,
    b_pawn7_col NUMBER(10)  DEFAULT 7,
    b_pawn7_move NUMBER(10) DEFAULT 0,
    b_pawn7_promotion VARCHAR2(20) DEFAULT 'pawn',
    b_pawn8_row NUMBER(10)  DEFAULT 2,
    b_pawn8_col NUMBER(10)  DEFAULT 8,
    b_pawn8_move NUMBER(10) DEFAULT 0,
    b_pawn8_promotion VARCHAR2(20) DEFAULT 'pawn',
    b_rook1_row NUMBER(10)  DEFAULT 1,
    b_rook1_col NUMBER(10)  DEFAULT 1,
    b_rook1_move NUMBER(10) DEFAULT 0,
    b_rook2_row NUMBER(10)  DEFAULT 1,
    b_rook2_col NUMBER(10)  DEFAULT 8,
    b_rook2_move NUMBER(10) DEFAULT 0,
    b_bishop1_row NUMBER(10)  DEFAULT 1,
    b_bishop1_col NUMBER(10)  DEFAULT 3,
    b_bishop1_move NUMBER(10) DEFAULT 0,
    b_bishop2_row NUMBER(10)  DEFAULT 1,
    b_bishop2_col NUMBER(10)  DEFAULT 6,
    b_bishop2_move NUMBER(10) DEFAULT 0,
    b_knight1_row NUMBER(10)  DEFAULT 1,
    b_knight1_col NUMBER(10)  DEFAULT 2,
    b_knight1_move NUMBER(10) DEFAULT 0,
    b_knight2_row NUMBER(10)  DEFAULT 1,
    b_knight2_col NUMBER(10)  DEFAULT 7,
    b_knight2_move NUMBER(10) DEFAULT 0,
    b_queen_row NUMBER(10)  DEFAULT 1,
    b_queen_col NUMBER(10)  DEFAULT 4,
    b_queen_move NUMBER(10) DEFAULT 0,
    b_king_row NUMBER(10)  DEFAULT 1,
    b_king_col NUMBER(10)  DEFAULT 5,
    b_king_move NUMBER(10) DEFAULT 0,
    FOREIGN KEY (gameno) REFERENCES history(gameno)
);

-- 기물 이동 경로 테이블 생성
CREATE TABLE move_board(
	gameno NUMBER(10) NOT null,
    turn NUMBER(10)  DEFAULT 0,
    row1  NUMBER(10)  DEFAULT 0,
    col1  NUMBER(10)  DEFAULT 0,
    row2  NUMBER(10)  DEFAULT 0,
    col2  NUMBER(10)  DEFAULT 0,
    FOREIGN KEY (gameno) REFERENCES history(gameno)
)

-- 게임 시작 시 각 기물 테이블 초기값 세팅 트리거
CREATE OR REPLACE TRIGGER trg_set_game
after INSERT
ON history
FOR EACH row
BEGIN
	INSERT INTO white(gameno) 
	values(:NEW.gameno);
	INSERT INTO black(gameno) 
	values(:NEW.gameno);
END;

-- 게임 시작 시 이동경로 초기값 세팅 트리거
CREATE OR REPLACE TRIGGER trg_set_move
after INSERT
ON history
FOR EACH row
BEGIN
	INSERT INTO move_board(gameno) 
	values(:NEW.gameno);
END;


-- 테이블 조회 쿼리
SELECT * FROM history ORDER BY gameno
SELECT * FROM white ORDER BY gameno
SELECT * FROM black ORDER BY gameno
SELECT * FROM move_board ORDER BY gameno

-- 데이터 삭제 쿼리
DELETE white;
DELETE black;
DELETE history;
DELETE move_board;

-- 게임 번호 시퀀스 생성 및 삭제 쿼리
DROP SEQUENCE history_gameno_sq;
CREATE SEQUENCE history_gameno_sq;


-- 체스 오라클 계정 생성 쿼리(해당 쿼리는 시스템 계정에서 진행)
CREATE USER chess IDENTIFIED BY 1234;
GRANT CONNECT, RESOURCE TO chess;
ALTER USER chess DEFAULT TABLESPACE users QUOTA UNLIMITED ON users;
ALTER USER chess ACCOUNT UNLOCK;

