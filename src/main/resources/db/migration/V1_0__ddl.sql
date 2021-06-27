CREATE  SEQUENCE hibernate_sequence;

CREATE TABLE book
(
   id BIGINT PRIMARY KEY,
   name VARCHAR(200),
   category VARCHAR(200),
   author VARCHAR(200),
   isbn VARCHAR(100),
   description VARCHAR
);