package com.github.alex_moon.porcupi.handlers;

import java.util.ArrayList;
import java.util.List;

import com.github.alex_moon.porcupi.manager.ManagerServer;

public class PokeHandler implements Handler {
    private Pokeable pokeable;
    private List<PokeContext> contexts = new ArrayList<PokeContext>();
    
    public PokeHandler(Pokeable pokeable) {
        this.pokeable = pokeable;
    }
    
    public String handle(long threadId, String key, List<String> tokens) {
        if (key.equals("poke")) {
            if (tokens.size() > 0) {
                String pokeKey = pokeable.poke(tokens);
                contexts.add(new PokeContext(threadId, this, pokeable, pokeKey));
                return "now poking " + pokeKey;
            }
            return "Not enough arguments to poke";
        }
        return "Didn't recognise key " + key;
    }
    
    public void activateContext(String pokeKey) {
        for (PokeContext context : contexts) {
            if (context.getKey() == pokeKey) {
                context.activate();
            }
        }
    }

    public Boolean canHandle(long threadId, String key) {
        return key.equals("poke");
    }
    
    public void tell(long threadId, Object message) {
        ManagerServer.get().tell(threadId, message.toString());
    }
    
    public void notify(long threadId, Object message) {
        // here's where we would notify all our contexts, you see?
    }
}
