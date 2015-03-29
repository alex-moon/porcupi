package com.github.alex_moon.porcupi.controllers;

import java.util.ArrayList;
import java.util.List;

import com.github.alex_moon.porcupi.controllers.Handler;

public class Controller {
    protected List<Handler> handlers = new ArrayList<Handler>();
    
    public Controller() {
        registerHandler(new PokeHandler());
    }

    // pub-sub stuff
    public void registerHandler(Handler handler) {
        handlers.add(handler);
    }
    
    private class PokeHandler implements Handler {
        public String handle(String key, Object data) {
            System.out.println("OH SHIT WE HAVE RECEIVED A POKE");
            return "ur shit gots pokesd";
        }
        
        public Boolean canHandle(String key) {
            return key.equals("poke");
        }
    }
    
    public List<String> handle(String key, Object data) {
        List<String> results = new ArrayList<String>();
        for (Handler handler : handlers) {
            if (handler.canHandle(key)) {
                results.add(handler.handle(key, data));
            }
        }
        return results;
    }
}
