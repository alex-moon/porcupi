package com.github.alex_moon.porcupi.messages;

public class PokeContextMessage extends PokeMessage {
    public PokeContextMessage(String message, long tid) {
        super(message, tid);
        setContext("poke");
    }
}
