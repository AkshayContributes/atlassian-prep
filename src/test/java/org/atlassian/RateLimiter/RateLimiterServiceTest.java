package org.atlassian.RateLimiter;

import org.atlassian.RateLimiter.strategy.RateLimiterFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RateLimiterServiceTest {
    RateLimiterService rateLimiterService;

    @BeforeEach
    public void setup() {
        this.rateLimiterService = new RateLimiterServiceImpl(new HashMap<>(), new RateLimiterFactory());
    }

    @Test
    void AddResource_ResourceIdLimitWindow_DoesNotThrow() {
        assertDoesNotThrow(() -> rateLimiterService.addResource("resource", "fixed-window-rate-limiter", "2,5"));
    }

    @Test
    void IsAllowed_ValidResourceAndHit_ReturnsTrue() {
        assertDoesNotThrow(() -> rateLimiterService.addResource("resource", "fixed-window-rate-limiter", "2,5"));
        assertTrue(rateLimiterService.isAllowed("resource", 1));
        assertTrue(rateLimiterService.isAllowed("resource", 2));
        assertTrue(rateLimiterService.isAllowed("resource", 7));
    }

    @Test
    void AddResource_UpgradeRateLimiter_DoesNotThrow() {
        assertDoesNotThrow(() -> rateLimiterService.addResource("resource", "fixed-window-rate-limiter", "2,5"));
        assertDoesNotThrow(() -> rateLimiterService.addResource("resource", "sliding-window-rate-limiter", "2,5"));
        assertTrue(rateLimiterService.isAllowed("resource", 1));
        assertTrue(rateLimiterService.isAllowed("resource", 2));
        assertTrue(rateLimiterService.isAllowed("resource", 6));

    }
}
