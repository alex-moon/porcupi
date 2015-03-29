package com.github.alex_moon.porcupi.handlers;

import java.util.List;

public class PokeHandler implements Handler {
    private Pokeable pokeable;
    
    public PokeHandler(Pokeable pokeable) {
        this.pokeable = pokeable;
    }
    
    public String handle(String key, List<String> tokens) {
        if (key.equals("poke")) {
            if (tokens.size() > 0) {
                return pokeable.poke(tokens);
            }
            return "Not enough arguments to poke";
        }
        return "Didn't recognise key " + key;
    }

    public Boolean canHandle(String key) {
        return key.equals("poke");
    }
}
