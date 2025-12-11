package org.atlassian.round5.model;

import java.time.LocalDateTime;

public class Court {
    private final int id;
    public LocalDateTime freeAt;

    public Court(int id, LocalDateTime freeAt) {
        this.id = id;
        this.freeAt = freeAt;
    }

    public Integer getId() {
        return this.id;
    }
}
