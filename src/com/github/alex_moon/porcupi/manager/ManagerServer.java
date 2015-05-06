package com.github.alex_moon.porcupi.manager;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

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
    
    public void tellOut(JSONObject output) {
        for (ManagerThread thread: threads) {
            if (!output.has("threadId") || thread.getId() == (long) output.get("threadId")) {
                thread.tellOut(output);
            }
        }
    }
    
    public void remove(ManagerThread thread) {
        threads.remove(thread);
    }
    
    public void registerHandler(Handler handler) {
        handlers.add(handler);
    }

    public void tellIn(JSONObject input) {
        // Thread thread, String key, List<String> tokens) {
        System.out.println("received from client: " + input.toString());
        for (Handler handler : handlers) {
            // somewhere in here we have to handle context-specific commands, you see?
            handler.tellIn(input);
        }
    }
}
