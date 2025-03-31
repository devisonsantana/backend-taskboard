package org.boardtask.app.entity;

import lombok.Data;

@Data
public class UserEntity {
    private Long id;
    private String name;
    private String password;
}
