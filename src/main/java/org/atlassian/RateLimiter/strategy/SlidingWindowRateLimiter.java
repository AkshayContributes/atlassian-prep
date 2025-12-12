package org.atlassian.RateLimiter.strategy;

import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindowRateLimiter implements RateLimiter {
    private final Deque<Integer> timestamps;
    private final int hitLimit;
    private final int timeWindow;
    public SlidingWindowRateLimiter(int hitLimit, int timeWindow) {
        this.timestamps = new ArrayDeque<>(hitLimit);
        this.hitLimit = hitLimit;
        this.timeWindow = timeWindow;
    }

    @Override
    public boolean isAllowed(int timestamp) {
        while(!timestamps.isEmpty() && timestamp - timestamps.getFirst() >= timeWindow) {
            timestamps.poll();
        }

        if(timestamps.size() == hitLimit) {
            return false;
        }

        timestamps.addLast(timestamp);

        return true;
    }
}
