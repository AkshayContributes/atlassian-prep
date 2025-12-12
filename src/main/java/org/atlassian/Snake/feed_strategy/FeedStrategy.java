package org.atlassian.Snake.feed_strategy;

import org.atlassian.Snake.engine.Game;
import org.atlassian.Snake.model.Cell;

import java.util.List;

public interface FeedStrategy {
    void generateFeed(Game game, int feedCount);

    List<Cell> getSpawnedFeed();
}
