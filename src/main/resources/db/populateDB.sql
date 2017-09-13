DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100001, TIMESTAMP '2017-05-07 15:36:38', 'Админ Ланч', 350);
INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100001, TIMESTAMP '2017-05-07 20:04:12', 'Админ Ужин', 500);

