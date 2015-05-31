package com.github.alex_moon.porcupi.messages;

public class MessageException extends Exception {
    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
