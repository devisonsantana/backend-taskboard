--liquibase formatted sql
--changeset dev:202503301649
--comment: user table create
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
) ENGINE=InnoDB;
--rollback DROP TABLE user