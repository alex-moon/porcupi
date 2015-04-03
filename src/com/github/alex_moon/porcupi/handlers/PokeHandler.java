package com.github.alex_moon.porcupi.handlers;

import java.util.List;

import com.github.alex_moon.porcupi.manager.ManagerServer;

public class PokeHandler implements Handler {
    private Pokeable pokeable;
    private List<Context> contexts;
    
    public PokeHandler(Pokeable pokeable) {
        this.pokeable = pokeable;
    }
    
    public String handle(long threadId, String key, List<String> tokens) {
        if (key.equals("poke")) {
            if (tokens.size() > 0) {
                String routeName = pokeable.poke(tokens);
                contexts.add(new PokeContext(threadId, this, routeName));
                return "now poking " + routeName;
            }
            return "Not enough arguments to poke";
        }
        return "Didn't recognise key " + key;
    }

    public Boolean canHandle(long threadId, String key) {
        return key.equals("poke");
    }
}
