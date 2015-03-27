package com.github.alex_moon.porcupi.manager;

import java.net.Socket;
import java.util.List;

import com.github.alex_moon.porcupi.transport.Transport;

public class ManagerThread extends Thread implements Manager {
    private ManagerServer server;
    private Socket socket;
    
    public Boolean poking = false;

    public ManagerThread(ManagerServer manager, Socket socket) {
        this.server = manager;
        this.socket = socket;
    }
    
    public void run() {
        try {
            Transport transport = new Transport(socket, this);
            transport.start();
            transport.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        server.remove(this);
    }
    
    public List<String> manage(String inputLine) {
        return server.manage(inputLine, null);
    }
}
