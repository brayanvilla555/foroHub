

INSERT INTO role VALUES (null,'ADMIN');
INSERT INTO role VALUES (null,'EDITOR');


INSERT INTO permission VALUES (null, 'TOPICOS');
INSERT INTO permission VALUES (null, 'RESPONSE');
INSERT INTO permission VALUES (null, 'USER');

INSERT INTO granted_permission(permission_id, role_id) VALUES (1,1);
INSERT INTO granted_permission(permission_id, role_id) VALUES (2,1);
INSERT INTO granted_permission(permission_id, role_id) VALUES (3,1);

INSERT INTO `user` (account_expired, account_locked, credentials_expired, enabled, role_id, name, password, username)VALUES (false, false, false, true, 1, 'Walter', '$2a$10$oNmXCXMWbC.ij1S06Kim4eJO6EoMt2JKaCR1JWf6LGVuKiViQgf5C','walter');

INSERT INTO `user` (account_expired, account_locked, credentials_expired, enabled, role_id, name, password, username)VALUES (false, false, false, true, 1, 'Brayan', '$2a$10$WlHV9dX7i/YiSWD08F6dJ.uPKOkbVd3zdNbT9rXQaKCgyrVkzccfW', 'villanueva');
