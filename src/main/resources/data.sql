SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE shopinfo;
TRUNCATE TABLE customers;
TRUNCATE TABLE reservation;

SET FOREIGN_KEY_CHECKS = 1;

-- ShopInfo
INSERT INTO shopinfo (id, business_hour, closed_days, deposit_amount, account_number)
VALUES (1, '평일: 10:00-21:00\n주말: 10:00-22:00', 1, 5000, '우리은행 1002-065-242977');

-- Customers
INSERT INTO customers (name, phone_num, noshow_count, kakao_user_id, created_at, updated_at)
VALUES ('정교은', '010-1111-2222', 0, 'kakao_001', NOW(), NOW());

INSERT INTO customers (name, phone_num, noshow_count, kakao_user_id, created_at, updated_at)
VALUES ('남민서', '010-2222-3333', 0, 'kakao_002', NOW(), NOW());

INSERT INTO customers (name, phone_num, noshow_count, kakao_user_id, created_at, updated_at)
VALUES ('김미지', '010-3333-4444', 0, 'kakao_003', NOW(), NOW());

INSERT INTO customers (name, phone_num, noshow_count, kakao_user_id, created_at, updated_at)
VALUES ('김지수', '010-4444-5555', 1, 'kakao_004', NOW(), NOW());

-- Reservation
INSERT INTO reservation (customer_id, name, phone_num, reserve_date, reserve_time, estimated_duration_min, service, off_removal, deposit_amount, visit_status, created_at, updated_at)
VALUES (1, '정교은', '010-1111-2222', '2026-05-13', '11:00-12:30', 90, '젤네일', false, 5000, 'PENDING', NOW(), NOW());

INSERT INTO reservation (customer_id, name, phone_num, reserve_date, reserve_time, estimated_duration_min, service, off_removal, deposit_amount, visit_status, created_at, updated_at)
VALUES (2, '남민서', '010-2222-3333', '2026-05-13', '14:00-15:30', 90, '아트네일', true, 5000, 'CONFIRMED', NOW(), NOW());

INSERT INTO reservation (customer_id, name, phone_num, reserve_date, reserve_time, estimated_duration_min, service, off_removal, deposit_amount, visit_status, created_at, updated_at)
VALUES (3, '김미지', '010-3333-4444', '2026-05-14', '10:00-11:00', 60, '젤네일', false, 5000, 'VISITED', NOW(), NOW());

INSERT INTO reservation (customer_id, name, phone_num, reserve_date, reserve_time, estimated_duration_min, service, off_removal, deposit_amount, visit_status, created_at, updated_at)
VALUES (4, '김지수', '010-4444-5555', '2026-05-14', '13:00-14:30', 90, '페디큐어', true, 5000, 'NO_SHOW', NOW(), NOW());