package com.github.alex_moon.porcupi.handlers;

import java.util.List;

public class PokeContext extends Context {
    private String routeName;

    public PokeContext(long threadId, PokeHandler handler, String routeName) {
        super(threadId, handler);
        this.routeName = routeName;
    }
    
    public String getRouteName() {
        return routeName;
    }

    public String handle(String key, List<String> tokens) {
        return null;
    }
}
