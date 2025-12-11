package org.atlassian.round3.feed_strategy;

import org.atlassian.round3.engine.Game;
import org.atlassian.round3.model.Cell;

import java.util.List;

public interface FeedStrategy {
    void generateFeed(Game game, int feedCount);

    List<Cell> getSpawnedFeed();
}
