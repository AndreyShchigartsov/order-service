package org.example.order.exception;

public class RemoteAccessException extends RuntimeException {
    public RemoteAccessException(String message) {
        super(message);
    }
}