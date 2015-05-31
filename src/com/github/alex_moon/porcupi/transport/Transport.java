package com.github.alex_moon.porcupi.transport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.github.alex_moon.porcupi.messages.Message;
import com.github.alex_moon.porcupi.Tellable;

public class Transport extends Thread {
    private Socket socket;
    private Tellable shell;
    private TransportThread thread;

    public Transport(Socket socket, Tellable shell) {
        this.socket = socket;
        this.shell = shell;
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

    public void tellIn(Message input) {
        shell.tellIn(input);
    }
    
    public void tellOut(Message output) {
        if (thread != null) {
            thread.tellOut(output);
        }
    }
}
