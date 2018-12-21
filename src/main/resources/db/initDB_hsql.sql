DROP TABLE user_roles
IF EXISTS;
DROP TABLE votes
IF EXISTS;
DROP TABLE users
IF EXISTS;
DROP TABLE menu
IF EXISTS;
DROP TABLE dish
IF EXISTS;
DROP TABLE restaurant
IF EXISTS;
DROP SEQUENCE global_seq
IF EXISTS;

CREATE SEQUENCE global_seq
  AS INTEGER
  START WITH 100000;

CREATE TABLE users
(
  id         INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  name       VARCHAR(255)               NOT NULL,
  email      VARCHAR(255)               NOT NULL,
  password   VARCHAR(255)               NOT NULL,
  registered TIMESTAMP DEFAULT now()    NOT NULL,
  enabled    BOOLEAN DEFAULT TRUE       NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx
  ON USERS (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR(255),
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id)
    ON DELETE CASCADE
);

CREATE TABLE restaurant (
  id           INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  name         VARCHAR(255) NOT NULL,
  address      VARCHAR(255) NOT NULL,
  phone_number VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX restaurant_unique_name_address_idx
  ON restaurant (name, address);

CREATE TABLE dish
(
  id        INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  dish_name VARCHAR(255) NOT NULL
);

CREATE TABLE menu
(
  id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  restaurant_id INTEGER            NOT NULL,
  date          DATE DEFAULT now() NOT NULL,
  dish_id       INTEGER            NOT NULL,
  dish_price    INTEGER            NOT NULL,
  FOREIGN KEY (restaurant_id) REFERENCES restaurant (id)
    ON DELETE CASCADE,
  FOREIGN KEY (dish_id) REFERENCES dish (id)
    ON DELETE CASCADE
);

CREATE INDEX menu_unique_restaurant_id_date_idx
  ON menu (restaurant_id, date);


CREATE INDEX dish_unique_dish_name_idx
  ON dish (dish_name);

CREATE TABLE votes (
  id             INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  user_id        INTEGER                 NOT NULL,
  restaurant_id  INTEGER                 NOT NULL,
  vote_date_time TIMESTAMP DEFAULT now() NOT NULL,
  FOREIGN KEY (user_id) REFERENCES USERS (id)
    ON DELETE CASCADE,
  FOREIGN KEY (restaurant_id) REFERENCES RESTAURANT (id)
    ON DELETE CASCADE,
  CONSTRAINT votes_user_restaurant_vote_date_time_idx UNIQUE (user_id, restaurant_id, vote_date_time)
);
