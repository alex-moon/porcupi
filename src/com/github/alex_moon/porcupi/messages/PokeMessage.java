package com.github.alex_moon.porcupi.messages;

public class PokeMessage extends Message {
    protected String action = "poke";
    public PokeMessage(String message) {
        super(message);
    }
}
