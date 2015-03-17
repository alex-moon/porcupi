package com.github.alex_moon.porcupi.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.github.alex_moon.porcupi.Config;

public class ManagerClient {
    public ManagerClient() {
        try (
            Socket socket = new Socket("127.0.0.1", Config.managerPort);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;
 
            System.out.println("Got a connection");
            while ((fromUser = stdIn.readLine()) != null) {
                System.out.println("Client: " + fromUser);
                out.println(fromUser);

                if ((fromServer = in.readLine()) != null) {
                    System.out.println("Server: " + fromServer);
                } else {
                    break;
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
