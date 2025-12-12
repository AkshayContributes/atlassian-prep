package org.atlassian.round8.strategy;

public interface RateLimiter {
    boolean isAllowed(int timestamp);
}
