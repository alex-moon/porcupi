package com.github.alex_moon.porcupi.handlers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.github.alex_moon.porcupi.manager.ManagerServer;

public class PokeHandler implements Handler {
    private Pokeable pokeable;
    private List<PokeContext> contexts = new ArrayList<PokeContext>();
    
    public PokeHandler(Pokeable pokeable) {
        this.pokeable = pokeable;
    }
    
    public void tellIn(JSONObject input) {
        long threadId = (long) input.get("threadId");
        String action = (String) input.get("action");
        if (action.equals("poke")) {
            String pokeKey = pokeable.poke((List<String>) input.get("tokens"));
            contexts.add(new PokeContext(threadId, this, pokeable, pokeKey));
            tellOut(new JSONObject("{\"poke\": \"" + pokeKey + "\"}"));
        }
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
    
    public void tellOut(JSONObject output) {
        ManagerServer.get().tellOut(output);
    }
    
    public void notify(long threadId, Object message) {
        // here's where we would notify all our contexts, you see?
    }
}
