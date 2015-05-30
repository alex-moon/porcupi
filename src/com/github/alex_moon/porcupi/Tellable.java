package com.github.alex_moon.porcupi;

import com.github.alex_moon.porcupi.messages.Message;

public interface Tellable {
    public void tellIn(Message input);
    public void tellOut(Message output);
}
