package org.atlassian.RateLimiter.strategy;

public interface RateLimiter {
    boolean isAllowed(int timestamp);
}
