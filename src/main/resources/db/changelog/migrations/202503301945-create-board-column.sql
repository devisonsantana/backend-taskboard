--liquibase formatted sql
--changeset dev:202503301945
--comment: board_column table create
CREATE TABLE board_column (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    `order` INT NOT NULL,
    kind VARCHAR(14) NOT NULL,
    board_id BIGINT NOT NULL,
    CONSTRAINT fk_board_id FOREIGN KEY (board_id) REFERENCES board (id) ON DELETE CASCADE,
    CONSTRAINT uk_order_id UNIQUE KEY unique_board_id_order (board_id, `order`)
) ENGINE=InnoDB;
--rollback DROP TABLE board_column