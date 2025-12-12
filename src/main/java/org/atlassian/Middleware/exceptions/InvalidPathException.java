package org.atlassian.Middleware.exceptions;

public class InvalidPathException extends RuntimeException{
    public InvalidPathException(String message) {
        super(message);
    }
}
