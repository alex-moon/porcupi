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

public class ManagerThread extends Thread implements Manager {
    private ManagerServer server;
    private Socket socket;
    
    public Boolean poking = false;

    public ManagerThread(ManagerServer manager, Socket socket) {
        this.server = manager;
        this.socket = socket;
    }
    
    public void run() {
        Transport transport = new Transport(socket, this);
        server.remove(this);
    }
    
    public List<String> manage(String inputLine) {
        return server.tell(inputLine, null);
    }
}
