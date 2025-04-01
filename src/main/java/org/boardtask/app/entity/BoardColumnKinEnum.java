package org.boardtask.app.entity;

import java.util.stream.Stream;

public enum BoardColumnKinEnum {
    TODO, IN_PROGRESS, DONE, CANCEL;

    public static BoardColumnKinEnum findByName(String name) {
        return Stream.of(BoardColumnKinEnum.values())
                .filter(b -> b.name().equals(name))
                .findFirst().orElseThrow();
    }
}
