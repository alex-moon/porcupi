package com.github.alex_moon.porcupi.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class Transport {
    private Socket socket;
    private Manager manager;
    private Gson gson = new Gson();

    public Transport(Socket socket, Manager manager) {
        this.socket = socket;
        this.manager = manager;
        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // @todo we need three separate threads running here - two for the socket itself and one for input
    public List<String> tell(String inputLine) {
        return new ArrayList<String>();
    }
}
