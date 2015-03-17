package com.github.alex_moon.porcupi.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ManagerThread extends Thread {
    private Manager manager;
    private Socket socket;

    public ManagerThread(Manager manager, Socket socket) {
        this.manager = manager;
        this.socket = socket;
    }
    
    public void run() {
        Gson gson = new Gson();
        String inputLine, key;
        List<String> results;
        Object data;
        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            System.out.println("We have a new connection:");
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (inputLine.contains("|")) {
                    key = inputLine.split("|")[0];
                    data = gson.fromJson(inputLine.split("|")[1], JsonObject.class);
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
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
