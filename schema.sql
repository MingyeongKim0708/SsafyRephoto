DROP DATABASE IF EXISTS PJT;

CREATE DATABASE PJT;

USE PJT;

CREATE TABLE user (
	user_id VARCHAR(50) PRIMARY KEY,
    user_password VARCHAR(50) NOT NULL,
    user_nick VARCHAR(50) UNIQUE NOT NULL,
    user_email VARCHAR(50) NOT NULL,
    user_created_at TIMESTAMP DEFAULT now(),
    authority VARCHAR(10) NOT NULL
);

CREATE TABLE board (
	  board_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    user_nick VARCHAR(50) NOT NULL,
    board_title VARCHAR(50) NOT NULL,
    board_info TEXT NOT NULL,
    board_created_at TIMESTAMP DEFAULT now(),
    view_cnt INTEGER NOT NULL DEFAULT 0,
    avg_score FLOAT NOT NULL DEFAULT 0, 
    FOREIGN KEY (user_nick) REFERENCES user(user_nick) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE profile (
	user_id VARCHAR(50) PRIMARY KEY,
	user_img VARCHAR(50), 
	user_uuid VARCHAR(50) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
);

CREATE TABLE photo(
	board_id INTEGER PRIMARY KEY, 
	photo_name VARCHAR(50) NOT NULL,
	photo_uuid VARCHAR(50) NOT NULL,
    FOREIGN KEY (board_id) REFERENCES board(board_id) ON DELETE CASCADE
);
 
CREATE TABLE comment (
	comment_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    board_id INTEGER,
    user_nick VARCHAR(50),
    score INTEGER NOT NULL,
    review VARCHAR(500) NOT NULL,
    comment_created_at TIMESTAMP DEFAULT now(),
    comment_updated_at TIMESTAMP DEFAULT now(),
    FOREIGN KEY (user_nick) REFERENCES user(user_nick) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (board_id) REFERENCES board(board_id) ON DELETE CASCADE ON UPDATE CASCADE
);





