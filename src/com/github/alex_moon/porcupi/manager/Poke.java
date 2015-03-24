package com.github.alex_moon.porcupi.manager;

import java.util.ArrayList;
import java.util.List;

public class Poke {
    public static List<Pokeable> pokeables = new ArrayList<Pokeable>();

    public static void poke(Pokeable pokeable) {
        pokeables.add(pokeable);
        Manager.getManager().pokeNotify();
    }
    
    public static List<Pokeable> getPokeables() {
        return pokeables;
    }
}
