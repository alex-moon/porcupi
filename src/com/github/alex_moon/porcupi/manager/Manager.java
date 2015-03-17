package com.github.alex_moon.porcupi.manager;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import com.github.alex_moon.porcupi.Config;
import com.github.alex_moon.porcupi.controllers.Controller;

public class Manager {
    private List<Controller> modules;
    private ServerSocket serverSocket;
    
    public Manager(List<Controller> modules) {
        this.modules = modules;
        try {
            serverSocket = new ServerSocket(Config.managerPort);
            while (true) {
                new ManagerThread(this, serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.out.println("Failed to open manager socket - no manager will be available for this session");
            e.printStackTrace();
        }
    }
    
    public List<String> tell(String key, Object data) {
        List<String> results = new ArrayList<String>();
        for (Controller module : modules) {
            for (String result : module.handle(key, data)) {
                results.add(result);
            }
        }
        return results;
    }
}
