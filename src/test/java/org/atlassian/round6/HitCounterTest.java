package org.atlassian.round6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HitCounterTest {
    HitCounter hitCounter;

    @BeforeEach
    public void setup() {
        hitCounter = new HitCounterImpl(new int[24 * 60 * 60]);
    }

    @Test
    void RegisterHit_Timestamp_DoesNotThrow() {
        assertDoesNotThrow(()->hitCounter.registerHit(1));
    }

    @Test
    void GetRecentHits_Timestamp_ReturnsRecentHits() {
        hitCounter.registerHit(1);
        hitCounter.registerHit(2);
        hitCounter.registerHit(3);
        assertEquals(3, hitCounter.getRecentHits(4));
        hitCounter.registerHit(300);
        assertEquals(4, hitCounter.getRecentHits(300));
        assertEquals(3, hitCounter.getRecentHits(301));
    }
}
