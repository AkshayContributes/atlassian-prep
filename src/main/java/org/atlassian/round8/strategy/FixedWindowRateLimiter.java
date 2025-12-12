package org.atlassian.round8.strategy;

public class FixedWindowRateLimiter implements RateLimiter {
    private final int hitLimit;
    private final int window;
    private int currentWindowStart = 0;
    private int requestCount = 0;

    public FixedWindowRateLimiter(int hitLimit, int window) {
        this.hitLimit = hitLimit;
        this.window = window;
    }

    @Override
    public boolean isAllowed(int timestamp) {
        int windowStart = (timestamp / window) * window;
        if(windowStart != currentWindowStart) {
            currentWindowStart = windowStart;
            requestCount = 0;
        }

        if(requestCount >= hitLimit) return false;

        requestCount++;
        return true;
    }
}
