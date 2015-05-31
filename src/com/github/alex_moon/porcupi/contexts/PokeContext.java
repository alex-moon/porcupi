package com.github.alex_moon.porcupi.contexts;

import java.util.Arrays;
import java.util.Map;

import com.github.alex_moon.porcupi.handlers.PokeHandler;
import com.github.alex_moon.porcupi.handlers.Pokeable;
import com.github.alex_moon.porcupi.messages.PokeContextMessage;


public class PokeContext extends Context {
    private String key;
    private Pokeable pokeable;

    public PokeContext(long tid, PokeHandler handler, Pokeable pokeable, String key) {
        super(tid, handler);
        this.context = "poke";
        this.key = key;
        this.pokeable = pokeable;
    }
    
    public String getKey() {
        return key;
    }
    
    public void handleMessage(String message) {
        // @todo not a fan of this super - get the superclass to call something
        super.handleMessage(message);
        String[] tokens = message.split(" ");
        if (tokens[0].equals("track")) {
            Map<String, Object> track = pokeable.getTrack();
            tokens = Arrays.copyOfRange(tokens, 1, tokens.length);
            for (String token : tokens) {
                if (track.containsKey(token)) {
                    handler.tellOut(
                        new PokeContextMessage(
                            token + ": " + track.get(token).toString(), tid
                        )
                    );
                } else {
                    handler.tellOut(
                        new PokeContextMessage(
                            token + " not tracked", tid
                        )
                    );
                }
            }
        }
    }
}
