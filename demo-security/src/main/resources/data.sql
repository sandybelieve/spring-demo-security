
  
INSERT INTO user(userid,username,password)
VALUES (1,'sandy','sandy');
INSERT INTO user(userid,username,password)
VALUES (2,'sandy2','sandy2');

INSERT INTO roles (roleid, role_name,user_role_id)
VALUES (1, 'ROLE_USER',1);
INSERT INTO roles (roleid, role_name,user_role_id)
VALUES (2, 'ROLE_ADMIN',1);
INSERT INTO roles (roleid, role_name,user_role_id)
VALUES (3, 'ROLE_USER',2);