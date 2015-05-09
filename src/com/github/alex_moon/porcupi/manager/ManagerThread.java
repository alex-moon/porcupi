package com.github.alex_moon.porcupi.manager;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.github.alex_moon.porcupi.messages.Message;

import com.github.alex_moon.porcupi.transport.Transport;

public class ManagerThread extends Thread implements Manager {
    private ManagerServer server;
    private Socket socket;
    private Transport transport;
    
    public Boolean poking = false;

    public ManagerThread(ManagerServer server, Socket socket) {
        this.server = server;
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
    
    public void tellOut(Message output) {
        transport.tellOut(output);
    }
    
    public void tellIn(Message input) {
        input.setManagerThreadId(getId());
        server.tellIn(input);
    }
}
