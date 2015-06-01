package com.github.alex_moon.porcupi.messages;

public class Ack extends Message {
    public Ack(long tid) {
        super("received", tid);
    }
}
