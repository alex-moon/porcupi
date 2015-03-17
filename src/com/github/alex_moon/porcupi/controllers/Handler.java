package com.github.alex_moon.porcupi.controllers;

public interface Handler {
    public String handle(String key, Object data);
    public Boolean canHandle(String key);
}
