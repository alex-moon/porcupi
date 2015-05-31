package com.github.alex_moon.porcupi.messages;

public class PokeMessage extends Message {
    public PokeMessage(String message) {
        super(message);
    }
    
    public PokeMessage(String message, long tid) {
        super(message, tid);
        try {
            setAction("poke");
        } catch (MessageException e) {
            e.printStackTrace();
        }
    }
}
