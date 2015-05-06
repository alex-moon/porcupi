package com.github.alex_moon.porcupi.transport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONObject;

public class TransportThread extends Thread {
    PrintWriter out;
    BufferedReader in;
    Transport transport;
    String inputLine;
    
    public TransportThread (BufferedReader in, PrintWriter out, Transport transport) {
        this.out = out;
        this.in = in;
        this.transport = transport;
    }
    
    @Override
    public void run() {
        try {
            while ((inputLine = in.readLine()) != null) {
                transport.tellIn(new JSONObject(inputLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tellOut(JSONObject output) {
        out.println(output.toString());
    }
}
