package com.github.alex_moon.porcupi.handlers;

import java.util.Arrays;
import java.util.Map;

import com.github.alex_moon.porcupi.messages.Message;


public class PokeContext extends Context {
    private String key;
    private Pokeable pokeable;

    public PokeContext(long tid, PokeHandler handler, Pokeable pokeable, String key) {
        super(tid, handler);
        this.key = key;
        this.pokeable = pokeable;
    }
    
    public String getKey() {
        return key;
    }
    
    public void handleMessage(String message) {
        String[] tokens = message.split(" ");
        if (tokens[0] == "track") {
            Map<String, Object> track = pokeable.getTrack();
            tokens = Arrays.copyOfRange(tokens, 1, tokens.length);
            for (String token : tokens) {
                if (track.containsKey(token)) {
                    handler.tellOut(
                        new Message(token + ": " + track.get(token).toString())
                        .setTid(tid)
                    );
                } else {
                    handler.tellOut(
                        new Message(token + " not tracked")
                        .setTid(tid)
                    );
                }
            }
        }
    }
}
