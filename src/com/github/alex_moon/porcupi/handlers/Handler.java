package com.github.alex_moon.porcupi.handlers;

import java.util.List;

public interface Handler {
    public String handle(long threadId, String key, List<String> tokens);
    public Boolean canHandle(long threadId, String key);
}
