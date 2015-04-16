package com.github.alex_moon.porcupi.handlers;

import java.util.List;

public interface Pokeable {
    public String poke(List<String> tokens);
    public Object getTrack();
}
