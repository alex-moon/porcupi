package com.github.alex_moon.porcupi.messages;

import java.util.HashMap;
import java.util.Map;

public class Message {
    protected String action;
    protected long managerThreadId = 0;
    protected String context;
    protected String message;
    protected Map<String, Object> track = new HashMap<String, Object>();
    
    public Message(String message) {
        this.message = message;
    }
    public Boolean isValid() {
        return action != null;
    }
    public String toString() {
        return String.format(
            "action=%s thread=%s context=%s message=%s",
            action,
            managerThreadId,
            context,
            message
        );
    }

    public String getAction() {
        return action;
    }
    public Message setAction(String action) {
        this.action = action;
        return this;
    }
    public long getManagerThreadId() {
        return managerThreadId;
    }
    public Message setManagerThreadId(long managerThreadId) {
        this.managerThreadId = managerThreadId;
        return this;
    }
    public String getContext() {
        return context;
    }
    public Message setContext(String context) {
        this.context = context;
        return this;
    }
    public Message setTrack(Map<String, Object> track) {
        this.track = track;
        return this;
    }
    public Map<String, Object> getTrack() {
        return track;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
