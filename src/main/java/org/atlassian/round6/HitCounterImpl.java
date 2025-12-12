package org.atlassian.round6;

public class HitCounterImpl implements HitCounter {
    private int[] times;
    private int[] hits;

    private static final int WINDOW = 300;


    public HitCounterImpl(int[] timestamps) {
        this.times = new int[WINDOW];
        this.hits = new int[WINDOW];
    }

    @Override
    public void registerHit(int timestamp) {
       int index = timestamp % WINDOW;
       if(times[index] != timestamp) {
           times[index] = timestamp;
           hits[index] = 0;
       }

       hits[index] += 1;
    }

    @Override
    public int getRecentHits(int timestamp) {
        int totalHits = 0;
        for(int i = 0; i<300; i++) {
            if(timestamp - times[i] < WINDOW) {
                totalHits += hits[i];
            }
        }

        return totalHits;
    }


}
