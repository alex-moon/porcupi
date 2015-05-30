package com.github.alex_moon.porcupi.shell;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import com.github.alex_moon.porcupi.Config;
import com.github.alex_moon.porcupi.Tellable;
import com.github.alex_moon.porcupi.handlers.Handler;
import com.github.alex_moon.porcupi.messages.Message;

public class ShellServer extends Thread implements Tellable {
    private static ShellServer server;
    private List<ShellThread> threads = new ArrayList<ShellThread>();
    private List<Handler> handlers = new ArrayList<Handler>();
    private ServerSocket serverSocket;
    
    public static ShellServer get() {
        return server;
    }
    
    public ShellServer() {
        server = this;
        this.start();
    }
    
    public void run() {
        try {
            serverSocket = new ServerSocket(Config.managerPort);
            while (true) {
                ShellThread newThread = new ShellThread(this, serverSocket.accept());
                threads.add(newThread);
                newThread.start();
            }
        } catch (IOException e) {
            System.out.println("Failed to open manager socket - no manager will be available for this session");
            e.printStackTrace();
        }
    }
    
    public void remove(ShellThread thread) {
        threads.remove(thread);
    }
    
    public void registerHandler(Handler handler) {
        handlers.add(handler);
    }

    public void tellIn(Message input) {
        System.out.println("received from client: " + input.toString());
        for (Handler handler : handlers) {
            handler.tellIn(input);
        }
    }
    
    public void tellOut(Message output) {
        for (ShellThread thread: threads) {
            if (
                output.getTid() == 0 ||
                thread.getId() == output.getTid()
            ) {
                thread.tellOut(output);
            }
        }
    }
}
