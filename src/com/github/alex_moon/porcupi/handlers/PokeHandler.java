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

        // @todo this next if statement is a
        // NullPointerException waiting to happen...
        if (input.get("action").equals("poke")) {
            String pokeKey = pokeable.poke(input.optString("message", null));
            contexts.add(new PokeContext(threadId, this, pokeable, pokeKey));
            tellOut(new JSONObject("{\"poke\": \"" + pokeKey + "\"}"));
        }
        // @todo context specific actions!!!
    }
    
    public void activateContext(String pokeKey) {
        for (PokeContext context : contexts) {
            if (context.getKey() == pokeKey) {
                context.activate();
            }
        }
    }
    
    public void tellOut(JSONObject output) {
        ManagerServer.get().tellOut(output);
    }
    
    public void notify(long threadId, Object message) {
        // @todo I'm not convinced we need this method - tellIn
        // should be able to handle it - we'd need a key for
        // "context" and I'm pretty sure that's all...
    }
}
