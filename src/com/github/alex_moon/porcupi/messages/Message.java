package com.github.alex_moon.porcupi.messages;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class Message {
    protected String action;
    protected long tid = 0;
    protected String context;
    protected String message;

    public static List<String> actions = Arrays.asList(
        "poke"
    );

    public static Message fromJson(String json) {
        return new Gson().fromJson(json, Message.class);
    }
    
    public Message(String message) {
        this.message = message;
    }
    public Boolean isValid() {
        return action != null;
    }
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getAction() {
        return action;
    }
    public Message setAction(String action) throws MessageException {
        if (!actions.contains(action)) {
            throw new MessageException("Invalid action " + action);
        }
        this.action = action;
        return this;
    }
    public long getTid() {
        return tid;
    }
    public Message setTid(long tid) {
        this.tid = tid;
        return this;
    }
    public String getContext() {
        return context;
    }
    public Message setContext(String context) {
        this.context = context;
        return this;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
