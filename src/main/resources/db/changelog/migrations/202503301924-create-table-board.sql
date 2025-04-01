--liquibase formatted sql
--changeset dev:202503301924
--comment: board table create
CREATE TABLE board (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
) ENGINE=InnoDB;
--rollback DROP TABLE board