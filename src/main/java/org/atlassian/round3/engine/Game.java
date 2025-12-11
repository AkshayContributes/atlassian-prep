package org.atlassian.round3.engine;

import org.atlassian.round3.exception.InvalidInputParamException;
import org.atlassian.round3.model.Cell;
import org.atlassian.round3.model.CellItem;
import org.atlassian.round3.model.Snake;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class Game {
    private final int totalCols;
    private final int totalRows;
    private final Snake snake;
    private final Map<String,Cell> cells;
    private int moveCount;


    public Game(int totalCols, int totalRows, int spawnCol, int spawnRow, int spawnLength) {
        this.totalCols = totalCols;
        this.totalRows = totalRows;
        this.snake = new Snake(new ArrayDeque<>());
        this.cells = new HashMap<>();
        this.moveCount = 0;
        spawnSnake(spawnCol, spawnRow, spawnLength);
    }

    private void spawnSnake(int spawnCol, int spawnRow, int spawnLength) {

        for(int i = 0; i<spawnLength; i++) {
            int newHeadCol = spawnCol + i;
            Cell cell = new Cell(spawnRow, newHeadCol);
            String key = getCellKey(spawnRow, newHeadCol);
            this.cells.putIfAbsent(key, cell);
            if(cells.get(key).getCellItem().equals(CellItem.SNAKE)) {
               throw new InvalidInputParamException("Invalid Spawn Parameters");
            }
            snake.moveHead(cell);
        }
    }

    public String getCellKey(int row, int col) {
        return String.format("%s_%s", row, col);
    }

    public Snake getSnake() {
        return this.snake;
    }

    public int getTotalRows() {
        return this.totalRows;
    }

    public int getTotalCols() {
        return this.totalCols;
    }

    public Map<String, Cell> getCells() {
        return this.cells;
    }

    public int getMoveCount() {
        return this.moveCount;
    }

    public void incrementMoveCount() {
        this.moveCount += 1;
    }
}
