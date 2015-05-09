package com.github.alex_moon.porcupi.handlers;

import java.util.ArrayList;
import java.util.List;

import com.github.alex_moon.porcupi.messages.Message;
import com.github.alex_moon.porcupi.messages.PokeMessage;

public abstract class Context {
    protected long tid;
    protected Handler handler;
    protected Object monitor;
    protected List<String> messages = new ArrayList<String>();
    protected Boolean continuing = false;

    public Context(long tid, Handler handler) {
        this.tid = tid;
        this.handler = handler;
    }
    
    public long getTid() {
        return tid;
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
    
    public void notify(Message messageObj) {
        messages.add(messageObj.getMessage());
        notify();
    }

    public void handleMessage(String message) {
        if (message == "continue") {
            continuing = true;
        }
        handler.tellOut(
            new PokeMessage("what are you doing lol")
        );
    }
}
