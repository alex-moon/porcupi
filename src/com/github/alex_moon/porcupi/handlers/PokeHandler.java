package com.github.alex_moon.porcupi.handlers;

import java.util.ArrayList;
import java.util.List;

import com.github.alex_moon.porcupi.manager.ManagerServer;
import com.github.alex_moon.porcupi.messages.Message;
import com.github.alex_moon.porcupi.messages.PokeMessage;

public class PokeHandler implements Handler {
    private Pokeable pokeable;
    private List<PokeContext> contexts = new ArrayList<PokeContext>();
    
    public PokeHandler(Pokeable pokeable) {
        this.pokeable = pokeable;
    }
    
    public void tellIn(Message input) {
        long tid = input.getTid();

        if (input.getAction().equals("poke")) {
            String pokeKey = pokeable.poke(input.getMessage());
            if (pokeKey != null) {
                contexts.add(new PokeContext(tid, this, pokeable, pokeKey));
                tellOut(new PokeMessage(pokeKey).setTid(tid));
            }
        }

        if (input.getContext() != null && input.getContext().equals("poke")) {
            for (PokeContext context : contexts) {
                if (input.getTid() == context.getTid()) {
                    synchronized(context) {
                        context.notify(input);
                    }
                }
            }
        }
    }
    
    public void activateContext(String pokeKey) {
        for (PokeContext context : contexts) {
            if (context.getKey() == pokeKey) {
                context.activate();
            }
        }
    }
    
    public void tellOut(Message output) {
        ManagerServer.get().tellOut(output);
    }
    
    public void notify(long tid, Object message) {
        // @todo I'm not convinced we need this method - tellIn
        // should be able to handle it - we'd need a key for
        // "context" and I'm pretty sure that's all...
    }
}
