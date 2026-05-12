SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE shopinfo;
TRUNCATE TABLE reservation;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO shopinfo (id, business_hour, closed_days, deposit_amount, account_number)
VALUES (1, '평일: 10:00-21:00\n주말: 10:00-22:00', 1, 5000, '우리은행 1002-065-242977');

INSERT INTO reservation (name, phone_num, reserve_date, reserve_time, estimated_duration_min, service, off_removal, deposit_amount, visit_status, created_at, updated_at)
VALUES ('홍길동', '010-1234-5678', '2026-05-13', '11:00-12:30', 90, '젤네일', false, 5000, 'PENDING', NOW(), NOW());