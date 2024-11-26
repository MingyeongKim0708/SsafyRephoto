USE PJT;

INSERT INTO user (user_id, user_password, user_nick, user_email, authority)
VALUES 
('admin', '1234512345', 'admin', 'admin@ssafy.com', 'admin'),
('ssafy1', '1234512345', '김싸피', 'ssafy1@ssafy.com', 'general'),
('ssafy2', '1234512345', '이싸피', 'ssafy2@ssafy.com', 'general'),
('ssafy3', '1234512345', '김싸프', 'ssafy3@ssafy.com', 'general'),
('ssafy4', '1234512345', '김탕피', 'ssafy4@ssafy.com', 'general');



INSERT INTO profile 
VALUES 
('admin','window.webp','0.webp'),
('ssafy1','window.webp','1.webp'),
('ssafy2','window.webp','2.webp'),
('ssafy3','window.webp','3.webp'),
('ssafy4','window.webp','0.webp');
        
        -- 트랜잭션 시작
START TRANSACTION;

-- 게시글 삽입 (board 테이블에)
INSERT INTO board (user_nick, board_title, board_info, avg_score)
VALUES 
('김싸피', '여명 속의 산', '해가 떠오르기 직전, 산의 윤곽이 붉은 빛으로 물들며 하루의 시작을 알립니다. 고요하고 장엄한 자연의 모습.', 4),
('김싸피', '도시의 밤하늘', '도심의 높은 건물들이 불빛을 내며 밤하늘을 수놓고 있습니다. 고요한 밤 속에서 도시의 활기를 느낄 수 있는 장면.', 3.33),
('김싸피', '꽃길 산책', '봄바람에 흩날리는 꽃잎들, 화사한 꽃들이 길을 따라 만개해 있습니다. 이 풍경은 마음을 편안하게 해줍니다.', 0),
('김싸피', '해변의 일몰', '붉은 태양이 바다에 녹아드는 순간, 하늘과 바다의 경계가 황홀하게 물듭니다. 고요한 바다의 물결이 편안함을 줍니다.', 0),
('김싸피', '안개 낀 숲길', '숲 속에 가득한 안개, 나무들 사이로 흐르는 신비로운 분위기. 고요한 숲의 아름다움이 절정에 달하는 순간입니다.', 0),
('이싸피', '밤의 산', '별빛이 쏟아지는 밤, 고요한 산이 별들과 함께 빛나며 자연의 광활함을 보여줍니다. 밤하늘을 바라보는 편안한 순간.', 6.67),
('이싸피', '산 정상에서의 일출', '산 정상에서 바라본 첫 햇살, 하늘이 차츰 밝아지며 세상의 모든 색깔이 다시 살아나는 모습입니다.', 0),
('이싸피', '고요한 호수의 아침', '호수 위로 아침 햇살이 부드럽게 퍼지고, 물결 하나 없이 고요한 풍경이 펼쳐집니다. 완벽하게 반사되는 자연의 아름다움.', 0),
('김싸프', '겨울의 숲', '눈 덮인 숲 속, 나무들이 흰 눈에 덮여 고요하고 차가운 겨울의 정수를 보여주는 풍경.', 4.5),
('김싸프', '폭포의 거친 물줄기', '거침없이 떨어지는 폭포의 물줄기, 그 강렬한 물보라가 자연의 힘과 아름다움을 동시에 느끼게 합니다.', 3.5),
('김싸프', '도시의 일몰', '붉게 물든 하늘과 빛나는 도시의 실루엣, 일몰의 황홀한 순간을 담은 도시 풍경입니다.', 0),
('김탕피', '평화로운 시골 풍경', '고요한 시골의 들판과 푸른 하늘, 평화롭고 여유로운 분위기가 가득한 풍경입니다.', 4.5),
('김탕피', '어두운 숲 속의 신비한 빛', '숲 속 깊은 곳, 빛 한 줄기가 나무들 사이로 스며드는 신비로운 분위기. 어둠 속에서도 희망의 빛을 찾을 수 있습니다.', 0),
('김탕피', '고산지대의 일출', '높은 산의 정상에서 맞이한 일출, 차가운 공기 속에서 태양이 떠오르는 순간, 마치 새로운 시작을 알리는 듯합니다.', 0),
('김탕피', '빛나는 도심', '도시의 높은 빌딩들이 밝은 불빛을 내며 저녁을 맞이하는 모습. 현대적인 도시의 에너지를 느낄 수 있는 장면.', 0);

INSERT INTO photo (board_id, photo_name, photo_uuid)
VALUES 
(1, 'sample1.webp', 'sample1.jpg'),
(2, 'sample2.webp', 'sample2.jpg'),
(3, 'sample3.webp', 'sample3.jpg'),
(4, 'sample4.webp', 'sample4.jpg'),
(5, 'sample5.webp', 'sample5.jpg'),
(6, 'sample6.webp', 'sample6.jpg'),
(7, 'sample7.webp', 'sample7.jpg'),
(8, 'sample8.webp', 'sample8.jpg'),
(9, 'sample9.webp', 'sample9.jpg'),
(10, 'sample10.webp', 'sample10.jpg'),
(11, 'sample11.webp', 'sample11.jpg'),
(12, 'sample12.webp', 'sample12.jpg'),
(13, 'sample13.webp', 'sample13.jpg'),
(14, 'sample14.webp', 'sample14.jpg'),
(15, 'sample15.webp', 'sample15.jpg');

-- 트랜잭션 커밋
COMMIT;

INSERT INTO comment (board_id, user_nick, score, review)
VALUES 
(1, '이싸피', 3, '산이 좋네요'),
(1, '김싸프', 4, '노을이 좋네요'),
(1, '김탕피', 5, '조화롭네요'),
(2, '이싸피', 2, '삭막하네'),
(2, '김싸프', 5, '평소의 도시랑 다르네요'),
(2, '이싸피', 3, '영화같아요'),
(6, '김싸피', 4, '밤하늘의별'),
(6, '김싸프', 2, '뭔가뭔가임'),
(6, '김탕피', 5, '별똥별좋지'),
(9, '김싸피', 4, '눈좋지'),
(9, '이싸피', 5, '구도가 좋네요'),
(10, '이싸피', 2, '그림티가나는데..'),
(10, '김탕피', 5, '시원하네'),
(12, '김싸피', 5, '축구하고싶다'),
(12, '이싸피', 4, '속이뻥');

SELECT *
FROM profile;

