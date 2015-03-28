package com.github.alex_moon.porcupi.manager;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import com.github.alex_moon.porcupi.Config;
import com.github.alex_moon.porcupi.controllers.Controller;

public class ManagerServer {
    private static ManagerServer server;
    private List<ManagerThread> threads = new ArrayList<ManagerThread>();
    private List<Controller> modules;
    private ServerSocket serverSocket;
    
    public static ManagerServer get() {
        return server;
    }
    
    public ManagerServer(List<Controller> modules) {
        server = this;
        this.modules = modules;
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
    
    public void remove(ManagerThread thread) {
        threads.remove(thread);
    }
    
    public List<String> manage(Thread thread, String key, Object data) {
        System.out.println("received from client: " + key);
        List<String> results = new ArrayList<String>();
        for (Controller module : modules) {
            for (String result : module.handle(key, data)) {
                results.add(result);
            }
        }
        return results;
    }
}
