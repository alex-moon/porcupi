package com.github.alex_moon.porcupi.messages;

public class Message {
    protected String action;
    protected String managerThreadId;
    protected String context;
    protected String message;
    
    public Message(String message) {
        this.message = message;
    }
    
    public Boolean isValid() {
        return action != null;
    }

    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public String getManagerThreadId() {
        return managerThreadId;
    }
    public void setManagerThreadId(String managerThreadId) {
        this.managerThreadId = managerThreadId;
    }
    public String getContext() {
        return context;
    }
    public void setContext(String context) {
        this.context = context;
    }
    
    
}
