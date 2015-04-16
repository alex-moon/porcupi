package com.github.alex_moon.porcupi.handlers;

import java.util.ArrayList;
import java.util.List;

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
    
    public void notify(String message) {
        // this is presently never called - we have to read from incoming socket and handle
        // in a context-specific manner
        messages.add(message);
        notify();
    }
    
    public void handleMessage(String message) {
        if (message == "continue") {
            continuing = true;
        }
    }
}
