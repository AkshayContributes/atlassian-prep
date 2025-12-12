package org.atlassian.Snake.model;

import java.util.ArrayDeque;

public class Snake {


    private final ArrayDeque<Cell> snakeCells;

    public Snake(ArrayDeque<Cell> snakeCells) {
        this.snakeCells = snakeCells;
    }

    public ArrayDeque<Cell> getSnakeCells() {
        return snakeCells;
    }


    public void moveHead(Cell cell) {
        this.snakeCells.addFirst(cell);
    }

    public Cell getCurrHead() {
        return this.snakeCells.getFirst();
    }

    public Cell moveTail() {
        return this.snakeCells.removeLast();
    }
}
