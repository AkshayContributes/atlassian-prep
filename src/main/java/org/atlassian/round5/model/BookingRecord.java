package org.atlassian.round5.model;

import java.time.LocalDateTime;

public class BookingRecord {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public BookingRecord(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }
}
