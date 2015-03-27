package com.github.alex_moon.porcupi.transport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TransportThread extends Thread {
    PrintWriter out;
    BufferedReader in;
    Transport transport;
    String inputLine;
    List<String> outputBuffer = new ArrayList<String>();
    
    public TransportThread (BufferedReader in, PrintWriter out, Transport transport) {
        this.out = out;
        this.in = in;
        this.transport = transport;
    }
    
    @Override
    public void run() {
        try {
            while ((inputLine = in.readLine()) != null) {
                transport.manage(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void tell(String outputLine) {
        out.println(outputLine);
    }
}
