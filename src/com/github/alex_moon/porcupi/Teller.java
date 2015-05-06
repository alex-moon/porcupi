package com.github.alex_moon.porcupi;

import org.json.JSONObject;

public interface Teller {
    public void tellIn(JSONObject input);
    public void tellOut(JSONObject output);
}
