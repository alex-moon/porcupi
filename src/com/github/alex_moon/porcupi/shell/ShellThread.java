package com.github.alex_moon.porcupi.shell;

import java.net.Socket;

import com.github.alex_moon.porcupi.Tellable;
import com.github.alex_moon.porcupi.messages.Message;
import com.github.alex_moon.porcupi.transport.Transport;

public class ShellThread extends Thread implements Tellable {
    private ShellServer server;
    private Socket socket;
    private Transport transport;
    
    public Boolean poking = false;

    public ShellThread(ShellServer server, Socket socket) {
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
        input.setTid(getId());
        server.tellIn(input);
    }
}
