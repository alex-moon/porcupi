package com.github.alex_moon.porcupi.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ManagerThread extends Thread {
    private Manager manager;
    private Socket socket;
    private Gson gson = new Gson();
    private Object monitor = new Object();
    
    public Boolean poking = false;

    public ManagerThread(Manager manager, Socket socket) {
        this.manager = manager;
        this.socket = socket;
    }
    
    public void run() {
        String inputLine;
        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            System.out.println("We have a new connection:");
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (inputLine.equals("poke")) {
                    startPoking(in, out);
                } else {
                    handleLine(inputLine, out);
                }
            }
            socket.close();
            manager.remove(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void pokeNotify() {
        synchronized (monitor) {
            monitor.notify();
        }
    }
    
    private void startPoking(BufferedReader in, PrintWriter out) {
        System.out.println("Start poking...");
        while (true) {
            synchronized (monitor) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("poke!");
        }
    }
    
    private void handleLine(String inputLine, PrintWriter out) {
        List<String> results;
        if (inputLine.contains("|")) {
            String key = inputLine.split("|")[0];
            Object data = gson.fromJson(inputLine.split("|")[1], JsonObject.class);
            results = manager.tell(key, data);
        } else {
            results = manager.tell(inputLine, null);
        }
        for (String result : results) {
            out.println(result);
            System.out.println("result: " + result);
        }
        out.println("done");
    }
}
