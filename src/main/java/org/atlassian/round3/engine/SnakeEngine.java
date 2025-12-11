package org.atlassian.round3.engine;

import org.atlassian.round3.model.Cell;
import org.atlassian.round3.model.Snake;

public interface SnakeEngine {
    void initGame(int totalRows, int totalCols, int spawnRow, int spawnCol, int spawnLength, int feedCount);

    Cell moveSnake(String r);

    Snake getSnake();
}
