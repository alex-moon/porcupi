package com.github.alex_moon.porcupi.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONObject;

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
                transport.tellOut(new JSONObject(inputLine));
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
    
    public void tellIn(JSONObject input) {
        System.out.println("received from server: " + input.toString());
    }
    
    public void tellOut(JSONObject output) {
        // if we're at the end of the chain, this method won't have meaning
        // @todo make Teller abstract and implement a stub?
        System.out.println("HOW DID THIS HAPPEN?: " + output.toString());
    }
}
