package org.atlassian.round8.strategy;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TokenBucketRateLimiter {
    private final int maxTokens;
    private final double refillRatePerSecond;
    private double currTokens;
    private int lastRefillTimestamp;

    private final ReentrantLock lock = new ReentrantLock();

    public TokenBucketRateLimiter(int maxTokens, double refillRate) {
        this.maxTokens = maxTokens;
        this.refillRatePerSecond = refillRate;
    }

    public boolean isAllowed(int timestamp) {

        lock.lock();
        try {
            refillTokens(timestamp);
            if(currTokens >= 1.0) {
                currTokens -= 1.0;
                return true;
            }

            return false;
        } finally {
            lock.unlock();
        }
    }

    private void refillTokens(int timestamp) {
        int timeElapsed = Math.max(0, timestamp - lastRefillTimestamp);
        if(timeElapsed == 0) return;
        double refilledTokens =(refillRatePerSecond) * timeElapsed;
        lastRefillTimestamp = timestamp;
        this.currTokens = Math.min(maxTokens, currTokens + refilledTokens);
    }


}
