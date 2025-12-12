package org.atlassian.Snake.model;

public class Cell {
    private final int row;
    private final int col;
    private CellItem cellItem;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.cellItem = CellItem.EMPTY;
    }


    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public CellItem getCellItem() {
        return this.cellItem;
    }

    public void setCellItem(CellItem cellItem) {
        this.cellItem = cellItem;
    }
}
