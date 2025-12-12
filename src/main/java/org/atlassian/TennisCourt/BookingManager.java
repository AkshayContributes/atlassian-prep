package org.atlassian.TennisCourt;

import org.atlassian.TennisCourt.model.BookingRecord;

import java.util.List;
import java.util.Map;

public interface BookingManager {
    Map<Integer, List<BookingRecord>> addBookings(List<BookingRecord> records);
}
