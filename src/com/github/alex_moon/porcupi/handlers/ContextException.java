package com.github.alex_moon.porcupi.handlers;

public class ContextException extends Exception {
    public ContextException(String message) {
        super(message);
    }

    public ContextException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
