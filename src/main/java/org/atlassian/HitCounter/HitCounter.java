package org.atlassian.HitCounter;

public interface HitCounter {
    void registerHit(int timestamp) ;

    int getRecentHits(int timestamp);
}
