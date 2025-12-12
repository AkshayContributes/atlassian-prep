package org.atlassian.TennisCourt;

import org.atlassian.Snake.exception.InvalidInputParamException;
import org.atlassian.TennisCourt.model.BookingRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingManagerTest {
    BookingManager bookingManager;

    @BeforeEach
    public void setup(){
        this.bookingManager = new BookingManagerImpl();
    }


    @Test
    void AddBookings_ValidBookingRecords_ReturnsBookingPlan() {
        List<BookingRecord> records = List.of(
                new BookingRecord(LocalDateTime.of(2025, 10, 10, 10, 0), LocalDateTime.of(2025, 10, 10, 10, 30)),
                new BookingRecord(LocalDateTime.of(2025, 10, 10, 10, 15), LocalDateTime.of(2025, 10, 10, 11, 0)),
                new BookingRecord(LocalDateTime.of(2025, 10, 10, 12, 15), LocalDateTime.of(2025, 10, 10, 13, 30)),
                new BookingRecord(LocalDateTime.of(2025, 10, 10, 10, 10), LocalDateTime.of(2025, 10, 10, 14, 30))

        );

        assertNotNull(bookingManager.addBookings(records));

        assertEquals(3, bookingManager.addBookings(records).size());
    }

    @Test
    void AddBookings_InvalidBookingRecord_ThrowsInvalidInputParamException() {
        List<BookingRecord> records = List.of(
                new BookingRecord(LocalDateTime.of(2025, 10, 10, 10, 0),
                        LocalDateTime.of(2025, 10, 10, 10, 30)),
                new BookingRecord(LocalDateTime.of(2025, 10, 10, 10, 15),
                        LocalDateTime.of(2025, 10, 10, 11, 0)),
                new BookingRecord(LocalDateTime.of(2025, 10, 10, 12, 15),
                        LocalDateTime.of(2025, 10, 10, 13, 30)),
                new BookingRecord(LocalDateTime.of(2025, 10, 10, 10, 10),
                        LocalDateTime.of(2025, 9, 10, 14, 30))
        );

        assertThrows(InvalidInputParamException.class, ()->bookingManager.addBookings(records));

    }
}
