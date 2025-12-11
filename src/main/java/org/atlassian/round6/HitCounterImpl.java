package org.atlassian.round6;

public class HitCounterImpl implements HitCounter {
    private int[] times;
    private int[] hits;


    public HitCounterImpl(int[] timestamps) {
        this.times = new int[300];
        this.hits = new int[300];
    }

    @Override
    public void registerHit(int timestamp) {
       int index = timestamp % 300;
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
            if(timestamp - times[i] < 300) {
                totalHits += hits[i];
            }
        }

        return totalHits;
    }


}
