package com.github.alex_moon.porcupi.transport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

import com.github.alex_moon.porcupi.manager.Manager;

public class Transport extends Thread {
    private Socket socket;
    private Manager manager;
    private TransportThread thread;

    public Transport(Socket socket, Manager manager) {
        this.socket = socket;
        this.manager = manager;
    }
    
    public void run() {
        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            thread = new TransportThread(in, out, this);
            thread.start();
            thread.join();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tellIn(JSONObject input) {
        manager.tellIn(input);
    }
    
    public void tellOut(JSONObject output) {
        if (thread != null) {
            thread.tellOut(output);
        }
    }
}
