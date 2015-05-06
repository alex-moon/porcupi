package com.github.alex_moon.porcupi.handlers;


public class PokeContext extends Context {
    private String key;
    private Pokeable pokeable;

    public PokeContext(long threadId, PokeHandler handler, Pokeable pokeable, String key) {
        super(threadId, handler);
        this.key = key;
        this.pokeable = pokeable;
    }
    
    public String getKey() {
        return key;
    }
    
    public void handleMessage(String message) {
        if (message == "track") {
            ((PokeHandler) handler).tellOut(threadId, pokeable.getTrack());
        }
    }
}
