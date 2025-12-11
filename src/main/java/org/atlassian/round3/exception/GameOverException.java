package org.atlassian.round3.exception;

public class GameOverException extends RuntimeException {
    public GameOverException(String message) {
        super(message);
    }
}
