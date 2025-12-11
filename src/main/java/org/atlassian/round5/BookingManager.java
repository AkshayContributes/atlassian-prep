package org.atlassian.round5;

import org.atlassian.round5.model.BookingRecord;

import java.util.List;
import java.util.Map;

public interface BookingManager {
    Map<Integer, List<BookingRecord>> addBookings(List<BookingRecord> records);
}
