package org.atlassian.Snake.engine;

import org.atlassian.Snake.model.Cell;
import org.atlassian.Snake.model.Snake;

public interface SnakeEngine {
    void initGame(int totalRows, int totalCols, int spawnRow, int spawnCol, int spawnLength, int feedCount);

    Cell moveSnake(String r);

    Snake getSnake();
}
