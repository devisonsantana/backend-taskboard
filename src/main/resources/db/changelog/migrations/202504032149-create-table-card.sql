--liquibase formatted sql
--changeset dev:202504032149
--comment: card table create
CREATE TABLE card (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    board_column_id BIGINT NOT NULL,
    CONSTRAINT fk_board_column_id FOREIGN KEY (board_column_id) 
    REFERENCES board_column (id)
    ON DELETE CASCADE
) ENGINE=InnoDB;
--rollback DROP TABLE card