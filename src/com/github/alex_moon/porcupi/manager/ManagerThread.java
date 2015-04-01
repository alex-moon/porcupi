package com.github.alex_moon.porcupi.manager;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.github.alex_moon.porcupi.transport.Transport;

public class ManagerThread extends Thread implements Manager {
    private ManagerServer server;
    private Socket socket;
    private Transport transport;
    
    public Boolean poking = false;

    public ManagerThread(ManagerServer manager, Socket socket) {
        this.server = manager;
        this.socket = socket;
    }
    
    public void run() {
        try {
            transport = new Transport(socket, this);
            transport.start();
            transport.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        server.remove(this);
    }
    
    public void tell(String outputLine) {
        transport.tell(outputLine);
    }
    
    public List<String> manage(String inputLine) {
        String key = null;
        List<String> tokens = new ArrayList<String>();
        for (String token : inputLine.split(" ")) {
            if (key == null) {
                key = token;
            } else {
                tokens.add(token);
            }
        }
        return server.manage(this, key, tokens);
    }
}
