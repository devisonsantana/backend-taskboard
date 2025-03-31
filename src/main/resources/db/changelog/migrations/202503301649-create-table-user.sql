--liquibase formatted sql
--changeset dev:202503301649
--comment: user table create
CREATE TABLE USER (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
    PASSWORD VARCHAR(255) NOT NULL
) ENGINE=InnoDB;
--rollback DROP TABLE USER