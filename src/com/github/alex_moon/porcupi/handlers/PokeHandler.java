package com.github.alex_moon.porcupi.handlers;

import java.util.HashMap;
import java.util.Map;

import com.github.alex_moon.porcupi.contexts.PokeContext;
import com.github.alex_moon.porcupi.messages.Message;
import com.github.alex_moon.porcupi.messages.PokeMessage;
import com.github.alex_moon.porcupi.shell.ShellServer;

public class PokeHandler implements Handler {
    private Pokeable pokeable;
    private Map<String, PokeContext> contexts = new HashMap<String, PokeContext>();
    
    public PokeHandler(Pokeable pokeable) {
        this.pokeable = pokeable;
    }
    
    public void tellIn(Message input) {
        long tid = input.getTid();

        if (input.getAction().equals("poke")) {
            String pokeKey = pokeable.poke(input.getMessage());
            if (pokeKey != null) {
                contexts.put(pokeKey, new PokeContext(tid, this, pokeable, pokeKey));
                tellOut(new PokeMessage(pokeKey, tid));
            }
        }

        if (input.getContext() != null && input.getContext().equals("poke")) {
            // @todo not really convinced by this - why not
            // put the pokeKey in the incoming message?
            for (PokeContext context : contexts.values()) {
                if (input.getTid() == context.getTid()) {
                    synchronized(context) {
                        context.notify(input);
                    }
                }
            }
        }
    }
    
    public void activateContext(String pokeKey) {
        if (contexts.containsKey(pokeKey)) {
            contexts.get(pokeKey).activate();
        }
    }
    
    public void tellOut(Message output) {
        ShellServer.get().tellOut(output);
    }
}
