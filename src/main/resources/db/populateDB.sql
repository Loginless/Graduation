DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM users;
DELETE FROM dish;
DELETE FROM menu;
DELETE FROM restaurant;
ALTER SEQUENCE global_seq
RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('Admin', 'admin@gmail.com', 'admin'),
  ('User', 'user@yandex.ru', 'password'),
  ('User1', 'user1@yandex.ru', 'password1');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_ADMIN', 100000),
  ('ROLE_USER', 100000),
  ('ROLE_USER', 100001),
  ('ROLE_USER', 100002);

INSERT INTO restaurant (name, address, phone_number)
VALUES ('Games with Fire', 'Kiev, Khreschatyk, 22', '+380445223456'),
  ('Menya Musashi', 'Kiev, Peremohy Ave, 31', '+380442234356'),
  ('Very Well', 'Kiev, Borshagivska str, 22', '+380442231326');

INSERT INTO dish (dish_name)
VALUES
  ('Steak'),
  ('Fish'),
  ('Borsh'),
  ('Potato'),
  ('Salad'),
  ('Juice'),
  ('Ramen'),
  ('Noodles'),
  ('Rice'),
  ('Soup'),
  ('Chicken with Potato');

INSERT INTO menu (restaurant_id, date, dish_id, dish_price)
VALUES
  (100003, '2018-12-01', 100006, 150),
  (100003, '2018-12-01', 100007, 80),
  (100003, '2018-12-01', 100008, 90),
  (100003, '2018-12-02', 100009, 150),
  (100003, '2018-12-02', 100010, 80),
  (100003, '2018-12-02', 100011, 90),
  (100004, '2018-12-01', 100012, 125),
  (100004, '2018-12-01', 100013, 135),
  (100004, '2018-12-01', 100014, 80),
  (100004, '2018-12-02', 100012, 125),
  (100004, '2018-12-02', 100013, 135),
  (100004, '2018-12-02', 100014, 80),
  (100005, '2018-12-01', 100010, 122),
  (100005, '2018-12-01', 100015, 50),
  (100005, '2018-12-02', 100010, 122),
  (100005, '2018-12-02', 100016, 134),
  (100005, '2018-12-03', 100010, 333),
  (100004, '2018-12-03', 100016, 444),
  (100005, CURRENT_DATE, 100010, 333);


INSERT INTO votes (user_id, restaurant_id, vote_date)
VALUES
  (100001, 100003, '2018-12-01'),
  (100002, 100005, '2018-12-01'),
  (100001, 100004, '2018-12-02'),
  (100002, 100004, '2018-12-02'),
  (100000, 100003, '2018-12-02'),
  (100001, 100004, '2018-12-03'),
  (100002, 100004, '2018-12-03'),
  (100001, 100005, CURRENT_DATE);

