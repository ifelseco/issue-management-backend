INSERT INTO user (id, account_non_locked, deleted_user, enabled, firstname, lastname,email, username,password,phone)
VALUES (1000,1, 1, 1, 'fatih', 'se','fatih@posta.com','fatih@posta.com','$2a$10$2wjebL7o4No1w3DSfTFtxO4z5C7546BACzUlxSN8vW74v0cgVKLbC', '0555555555');
INSERT INTO user (id, account_non_locked, deleted_user, enabled, firstname, lastname,email, username,password,phone)
VALUES (2000, 1, 1, 1,'moon', 'ko','moon@posta.com','moon@posta.com','$2a$10$2wjebL7o4No1w3DSfTFtxO4z5C7546BACzUlxSN8vW74v0cgVKLbC', '0555555555');
INSERT INTO user (id, account_non_locked, deleted_user, enabled, firstname, lastname,email, username,password,phone)
VALUES (3000, 1, 1, 1, 'bekir','ar','bekir@posta.com','bekir@posta.com','$2a$10$2wjebL7o4No1w3DSfTFtxO4z5C7546BACzUlxSN8vW74v0cgVKLbC', '0555555555');

INSERT INTO team (id, create_time, created_by, name)
VALUES (150, '2021-01-23 22:22:04', 1000,'team1');
INSERT INTO team (id, create_time, created_by, name)
VALUES (250, '2021-01-23 22:22:04', 2000,'team2');
INSERT INTO team (id, create_time, created_by, name)
VALUES (350, '2021-01-23 22:22:04', 3000,'team3');