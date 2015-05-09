package com.github.alex_moon.porcupi.messages;

public class PokeMessage extends Message {
    public PokeMessage(String message) {
        super(message);
        try {
            setAction("poke");
        } catch (MessageException e) {
            System.out.println("You fucking broke shit you dumb bastard");
        }
    }
}
