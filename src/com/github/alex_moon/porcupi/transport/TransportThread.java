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
    
    public TransportThread (BufferedReader in, PrintWriter out, Transport transport) {
        this.out = out;
        this.in = in;
        this.transport = transport;
    }
    
    @Override
    public void run() {
        try {
            while ((inputLine = in.readLine()) != null) {
                tell(transport.manage(inputLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void tell(String outputLine) {
        out.println(outputLine);
    }
    
    public void tell(List<String> outputLines) {
        if (outputLines != null) {
            for (String outputLine : outputLines) {
                out.println(outputLine);
            }
        }
    }
}
