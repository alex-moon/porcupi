package com.github.alex_moon.porcupi.handlers;

import java.util.Map;

public interface Pokeable {
    public String poke(String message);
    public Map<String, Object> getTrack();
}
