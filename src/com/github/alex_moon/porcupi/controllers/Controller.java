package com.github.alex_moon.porcupi.controllers;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    protected List<Handler> handlers = new ArrayList<Handler>();

    // pub-sub stuff
    public void registerHandler(Handler handler) {
        handlers.add(handler);
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
