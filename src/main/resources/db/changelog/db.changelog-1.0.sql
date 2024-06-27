--liquibase formatted sql

--changeset Maria_solsol:1
CREATE SEQUENCE id_seq
    AS BIGINT
    START WITH 100000;
--rollback DROP SEQUENCE id_seq;

--changeset Maria_solsol:2
CREATE TABLE users
(
    id       BIGINT PRIMARY KEY nextval('id_seq'),
    email    VARCHAR(320) NOT NULL,
    password VARCHAR      NOT NULL,

);
--rollback DROP TABLE users

--changeset Maria_solsol:3
CREATE TYPE space_type AS ENUM(
       'WORKING_SPACE',
    'CONFERENCE_HALL');
CREATE TABLE space
(
    id       BIGINT PRIMARY KEY nextval('id_seq'),
    location   VARCHAR(255) NOT NULL,
    space_type space_type   NOT NULL
);
--rollback DROP TYPE space_type; DROP TABLE space;

--changeset Maria_solsol:4
CREATE TABLE booking
(
    id BIGINT PRIMARY KEY nextval('id_seq'),
    is_booked BOOLEAN NOT NULL,
    time_start TIMESTAMP NOT NULL,
    time_end TIMESTAMP NOT NULL,
    space_id BIGINT NOT NULL REFERENCES space(id),
    for_user_id BIGINT REFERENCES users(id)

);
--rollback DROP TABLE booking