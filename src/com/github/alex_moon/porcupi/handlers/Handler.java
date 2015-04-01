package com.github.alex_moon.porcupi.handlers;

import java.util.List;

public interface Handler {
    public String handle(String key, List<String> tokens);
    public Boolean canHandle(String key);
}
