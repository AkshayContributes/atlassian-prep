package org.atlassian.Snake.feed_strategy;

import org.atlassian.Snake.engine.Game;
import org.atlassian.Snake.model.Cell;
import org.atlassian.Snake.model.CellItem;

import java.util.*;

public class RandomFeedStrategy implements FeedStrategy {

    private static List<Cell> spawnedFeed = new ArrayList<>();

    @Override
    public void generateFeed(Game game, int feedCount) {
        int totalCols = game.getTotalCols();
        int totalRows = game.getTotalRows();
        Random random = new Random();

        for(int i = 0; i<feedCount; ) {
           int newRow = random.nextInt(totalRows);
           int newCol = random.nextInt(totalCols);
           String cellKey = game.getCellKey(newRow, newCol);
           game.getCells().putIfAbsent(cellKey, new Cell(newRow, newCol));
           Cell cell = game.getCells().get(cellKey);
           if(cell.getCellItem().equals(CellItem.EMPTY)) {
               cell.setCellItem(CellItem.FODD);
               spawnedFeed.add(cell);
               i++;
           }
        }
    }

    public List<Cell> getSpawnedFeed() {
        return spawnedFeed;
    }
}
