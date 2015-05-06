package com.github.alex_moon.porcupi.handlers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public abstract class Context {
    protected long threadId;
    protected Handler handler;
    protected Object monitor;
    protected List<String> messages = new ArrayList<String>();
    protected Boolean continuing = false;

    public Context(long threadId, Handler handler) {
        this.threadId = threadId;
        this.handler = handler;
    }
    
    public void activate() {
        // block! We want to wait on incoming to the handler...
        synchronized(this) {
            while (!continuing) {
                try {
                    wait();
                    for (String message : messages) {
                        messages.remove(message);
                        this.handleMessage(message);
                    }
                } catch (InterruptedException e) {
                    return;
                }
            }
            continuing = false;
        }
    }
    
    public void notify(JSONObject messageObj) {
        // this is presently never called - we have to read from incoming socket and handle
        // in a context-specific manner
        if (messageObj.has("messages")) {
            for (String message : (List<String>) messageObj.get("messages")) {
                messages.add(message);
            }
        }
        
        if (messageObj.has("message")) {
            messages.add((String) messageObj.get("message"));
        }
        notify();
    }
    
    public void handleMessage(String message) {
        if (message == "continue") {
            continuing = true;
        }
        handler.tellOut(
            new JSONObject()
            .put("message", "what are you doing lol")
            .put("action", "poke")
        );
    }
}
