package org.atlassian.round6;

public interface HitCounter {
    void registerHit(int timestamp) ;

    int getRecentHits(int timestamp);
}
