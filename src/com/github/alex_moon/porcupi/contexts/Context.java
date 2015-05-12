package com.github.alex_moon.porcupi.contexts;

import java.util.ArrayList;
import java.util.List;

import com.github.alex_moon.porcupi.handlers.Handler;
import com.github.alex_moon.porcupi.messages.Message;
import com.github.alex_moon.porcupi.messages.PokeMessage;

public abstract class Context {
    protected long tid;
    protected Handler handler;
    protected Object monitor;
    protected List<String> messages = new ArrayList<String>();
    protected Boolean continuing = false;
    protected String context;

    public Context(long tid, Handler handler) {
        this.tid = tid;
        this.handler = handler;
    }
    
    public long getTid() {
        return tid;
    }

    public void activate() {
        System.out.println("--blocking--");
        // block! We want to wait on incoming to the handler...
        handler.tellOut(new Message("activated", tid).setContext(context));
        synchronized(this) {
            while (!continuing) {
                try {
                    wait();
                    for (String message : messages) {
                        this.handleMessage(message);
                    }
                    messages.clear();
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("--no longer blocking--");
            continuing = false;
        }
    }
    
    public void notify(Message input) {
        messages.add(input.getMessage());
        notify();
    }

    public void handleMessage(String message) {
        System.out.println("Trying to handle message " + message);
        if (message.equals("continue")) {
            continuing = true;
        }
    }
}
