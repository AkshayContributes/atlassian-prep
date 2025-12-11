package org.atlassian.round2.model;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Agent {
    private final String id;
    private RatingStats ratingStats;
    private Map<YearMonth, RatingStats> monthlyStatsMap;

    public Agent(String id) {
        this.id = id;
        this.ratingStats = new RatingStats();
        this.monthlyStatsMap = new HashMap<>();
    }

    public Double getAverageRating() {
        return this.ratingStats.getAverage();
    }

    public Double getAverageRatingByMonth(YearMonth month) {
        if(!this.monthlyStatsMap.containsKey(month)) {
            return 0.0;
        }
        return this.monthlyStatsMap.get(month).getAverage();
    }

    public String getId() {
        return this.id;
    }

    public void updateRating(int rating, LocalDate date) {
        this.updateGlobalStats(rating);
        YearMonth bucket = YearMonth.from(date);
        this.monthlyStatsMap.putIfAbsent(bucket, new RatingStats());
        this.monthlyStatsMap.get(bucket).updateRating(rating);
    }


    private void updateGlobalStats(int rating) {
       this.ratingStats.updateRating(rating);
    }

    @Override
    public boolean equals(Object o) {
        if(getClass() != o.getClass()) return false;

        return this.id.equals(((Agent) o).getId());
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }


    private static class RatingStats {
        double count = 0;
        double sum = 0;
        void updateRating(int rating) {
            sum += rating;
            count += 1;
        }
        double getAverage() {
           return count == 0.0 ? 0 : sum/count;
        }
    }
}
