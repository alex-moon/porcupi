package com.github.alex_moon.porcupi.manager;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import com.github.alex_moon.porcupi.Config;
import com.github.alex_moon.porcupi.handlers.Handler;

public class ManagerServer extends Thread {
    private static ManagerServer server;
    private List<ManagerThread> threads = new ArrayList<ManagerThread>();
    private List<Handler> handlers = new ArrayList<Handler>();
    private ServerSocket serverSocket;
    
    public static ManagerServer get() {
        return server;
    }
    
    public ManagerServer() {
        server = this;
        this.start();
    }
    
    public void run() {
        try {
            serverSocket = new ServerSocket(Config.managerPort);
            while (true) {
                ManagerThread newThread = new ManagerThread(this, serverSocket.accept());
                threads.add(newThread);
                newThread.start();
            }
        } catch (IOException e) {
            System.out.println("Failed to open manager socket - no manager will be available for this session");
            e.printStackTrace();
        }
    }
    
    public void tell(String output) {
        for (ManagerThread thread: threads) {
            thread.tell(output);
        }
    }
    
    public void remove(ManagerThread thread) {
        threads.remove(thread);
    }
    
    public void registerHandler(Handler handler) {
        handlers.add(handler);
    }
    
    public List<String> manage(Thread thread, String key, List<String> tokens) {
        System.out.println("received from client: " + key);
        List<String> results = new ArrayList<String>();
        for (Handler handler : handlers) {
            if (handler.canHandle(key)) {
                results.add(handler.handle(key, tokens));
            }
        }
        return results;
    }
}
