package org.atlassian.round5;

import org.atlassian.round3.exception.InvalidInputParamException;
import org.atlassian.round5.model.BookingRecord;
import org.atlassian.round5.model.Court;

import java.util.*;

public class BookingManagerImpl implements BookingManager {
    @Override
    public Map<Integer, List<BookingRecord>> addBookings(List<BookingRecord> records) {
        if(records == null || records.isEmpty()) {
            return Map.of();
        }

        List<BookingRecord> copyRecords = new ArrayList<>(records);

        copyRecords.sort(Comparator.comparing(BookingRecord::getStartTime));

        int numCourtId = 0;

        PriorityQueue<Court> sortedByEndTime = new PriorityQueue<>(Comparator.comparing(c -> c.freeAt));

        Map<Integer, List<BookingRecord>> bookingsByCourts = new HashMap<>();

        for(BookingRecord record: copyRecords) {
            if(record.getStartTime().isAfter(record.getEndTime())) {
                throw new InvalidInputParamException("Start Time can't be After End Time");
            }
            if(sortedByEndTime.isEmpty() || sortedByEndTime.peek().freeAt.isAfter(record.getStartTime())) {
                int newId = numCourtId++;
                bookingsByCourts.put(newId, new ArrayList<>());
                bookingsByCourts.get(newId).add(record);
                sortedByEndTime.offer(new Court(newId, record.getEndTime()));
            }else {
                Court activeCourt = sortedByEndTime.poll();
                if(activeCourt == null) {
                    throw new RuntimeException("No Active court");
                }
                activeCourt.freeAt = record.getEndTime();
                bookingsByCourts.get(activeCourt.getId()).add(record);
                sortedByEndTime.offer(activeCourt);
            }
        }

        return bookingsByCourts;

    }
}
