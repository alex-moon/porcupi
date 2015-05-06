package com.github.alex_moon.porcupi.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.github.alex_moon.porcupi.Config;
import com.github.alex_moon.porcupi.transport.Transport;

public class ManagerClient implements Manager {
    private String inputLine;

    public ManagerClient() {
        Socket socket;
        Transport transport;
        try {
            socket = new Socket("127.0.0.1", Config.managerPort);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            transport = new Transport(socket, this);
            transport.start();
            while ((inputLine = stdIn.readLine()) != null) {
                transport.tellOut(inputLine);
            }
            socket.close();
        } catch (UnknownHostException e1) {
            System.out.println("Couldn't find 127.0.0.1, viz. you've broken shit you stupid noob");
            System.exit(2);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }
    }
    
    public List<String> tellIn(JSONObject inputLine) {
        System.out.println("received from server: " + inputLine);
        return null;
    }
}
