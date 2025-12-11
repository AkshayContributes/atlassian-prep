package org.atlassian.round3;

import org.atlassian.round3.engine.SnakeEngine;
import org.atlassian.round3.engine.SnakeEngineImpl;
import org.atlassian.round3.feed_strategy.RandomFeedStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeEngineTest {
    SnakeEngine snakeEngine;

    @BeforeEach
    public void setup() {
        this.snakeEngine = new SnakeEngineImpl(new RandomFeedStrategy());
    }

    @Test
    void GameInit_ValidBoundsAndSpawn_DoesNotThrow() {
        assertDoesNotThrow(() -> snakeEngine.initGame(10, 10, 0, 0, 3, 5));
    }

    @Test
    void MoveSnake_ValidBoundsAndSpawn_ReturnsNewHeadCell() {
        snakeEngine.initGame(10, 10, 0, 0, 3, 5);
        assertEquals(3, snakeEngine.getSnake().getSnakeCells().size());
        assertEquals(3, snakeEngine.moveSnake("R").getCol());
        assertEquals(4, snakeEngine.moveSnake("R").getCol());
        assertEquals(5, snakeEngine.moveSnake("R").getCol());
        assertEquals(6, snakeEngine.moveSnake("R").getCol());
    }
}
