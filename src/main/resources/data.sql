INSERT IGNORE INTO users (birth_date, postal_code, created_at, id, updated_at, phone_number, id_number, account_holder, account_number, address, bank, email, name, nickname, password, rest, gender) VALUES(NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'jeonjhyeok@gmail.com', NULL, '3939', NULL, NULL, NULL);

INSERT IGNORE INTO code (orders, id, parent_id, code, value) VALUES(1, 1, NULL, 'CAMPAIGN_MISSION', '캠페인 미션');
INSERT IGNORE INTO code (orders, id, parent_id, code, value) VALUES(2, 2, NULL, 'CAMPAIGN_OPTION', '캠페인 옵션');
INSERT IGNORE INTO code (orders, id, parent_id, code, value) VALUES(1, 3, 2, 'TAKEOUT', '포장가능');
INSERT IGNORE INTO code (orders, id, parent_id, code, value) VALUES(2, 4, 2, 'RESERVATION', '예약필수');
INSERT IGNORE INTO code (orders, id, parent_id, code, value) VALUES(1, 5, 1, 'KEYWORD', '키워드 필수');
INSERT IGNORE INTO code (orders, id, parent_id, code, value) VALUES(2, 6, 1, 'PHOTO', '사진 {}장 이상');
INSERT IGNORE INTO code (orders, id, parent_id, code, value) VALUES(3, 7, 1, 'VIDEO', '글자수 {}자 이상');
INSERT IGNORE INTO code (orders, id, parent_id, code, value) VALUES(4, 8, 1, 'CHARACTER', '글자수 {}자 이상');
INSERT IGNORE INTO code (orders, id, parent_id, code, value) VALUES(5, 9, 1, 'MAP', '지도 필수');
INSERT IGNORE INTO code (orders, id, parent_id, code, value) VALUES(6, 10, 1, 'BANNER', '배너 필수');
INSERT IGNORE INTO code (orders, id, parent_id, code, value) VALUES(7, 11, 1, 'LINK', '링크 필수');