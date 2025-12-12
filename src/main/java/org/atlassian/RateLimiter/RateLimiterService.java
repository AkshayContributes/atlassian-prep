package org.atlassian.RateLimiter;

public interface RateLimiterService {
    void addResource(String resource, String rateLimiterType, String limits);

    boolean isAllowed(String resource, int timestamp);
}
