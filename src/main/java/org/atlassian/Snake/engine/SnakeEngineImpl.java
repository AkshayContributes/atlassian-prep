package org.atlassian.Snake.engine;

import org.atlassian.Snake.exception.GameOverException;
import org.atlassian.Snake.exception.InvalidInputParamException;
import org.atlassian.Snake.feed_strategy.FeedStrategy;
import org.atlassian.Snake.model.Cell;
import org.atlassian.Snake.model.CellItem;
import org.atlassian.Snake.model.Snake;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;

public class SnakeEngineImpl implements SnakeEngine {
    private Game game;
    private final FeedStrategy feedStrategy;
    private ArrayDeque<Cell> spawnedFeed;


    private static final Map<String, List<Integer>> dirs = Map.ofEntries(
            Map.entry("L", List.of(0, -1)),
            Map.entry("R", List.of(0, 1)),
            Map.entry("D", List.of(1, 0)),
            Map.entry("U", List.of(-1, 0))
    );

    public SnakeEngineImpl(FeedStrategy feedStrategy) {
        this.feedStrategy = feedStrategy;
    }

    @Override
    public void initGame(int totalRows, int totalCols, int spawnRow, int spawnCol, int spawnLength, int feedCount) {
        this.game = new Game(totalCols, totalRows, spawnCol, spawnRow, spawnLength);
        //Spawn the random feed here
        feedStrategy.generateFeed(game,feedCount);
        spawnedFeed = new ArrayDeque<>(feedStrategy.getSpawnedFeed());

    }

    @Override
    public Cell moveSnake(String dir) {
        if(!dirs.containsKey(dir)) {
            throw new InvalidInputParamException("Invalid Direction");
        }
        List<Integer> numericalDir = dirs.get(dir);

        Cell currHead = this.game.getSnake().getCurrHead();
        int newHeadRow = currHead.getRow() + numericalDir.getFirst();
        int newHeadCol = currHead.getCol() + numericalDir.getLast();

        newHeadRow = wrapIfNecessary(newHeadRow, this.game.getTotalRows());
        newHeadCol = wrapIfNecessary(newHeadCol, this.game.getTotalCols());

        Cell cell = new Cell(newHeadRow, newHeadCol);
        String cellKey = game.getCellKey(newHeadRow, newHeadCol);
        this.game.getCells().putIfAbsent(cellKey, cell);
        cell = this.game.getCells().get(cellKey);

        this.game.incrementMoveCount();

        if(cell.getCellItem().equals(CellItem.FODD)) {
            this.game.getSnake().moveTail().setCellItem(CellItem.EMPTY);
        }

        if(cell.getCellItem().equals(CellItem.SNAKE)) {
            throw new GameOverException("Snake Ate Itself. Game Over");
        }

        cell.setCellItem(CellItem.SNAKE);
        this.game.getSnake().moveHead(cell);
        return cell;
    }

    private int wrapIfNecessary(int index, int bounds) {
        if(index >= bounds) {
            index = 0;
        }else if(index < 0) {
            index = bounds - 1;
        }

        return index;
    }

    public Snake getSnake() {
        return this.game.getSnake();
    }
}
