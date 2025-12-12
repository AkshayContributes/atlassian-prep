package org.atlassian.RateLimiter.strategy;

import org.atlassian.Snake.exception.InvalidInputParamException;

public class RateLimiterFactory {
    public RateLimiter get(String rateLimiterType, int hitLimit, int timeWindow) {
        switch (rateLimiterType) {
            case "fixed-window-rate-limiter" -> {
                return new FixedWindowRateLimiter(hitLimit, timeWindow);
            }

            case "sliding-window-rate-limiter" -> {
                return new SlidingWindowRateLimiter(hitLimit, timeWindow);
            }

            default -> throw new InvalidInputParamException("Invalid Rate Limiter Type requested");
        }
    }
}
