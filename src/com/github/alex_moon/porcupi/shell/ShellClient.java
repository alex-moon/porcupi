package com.github.alex_moon.porcupi.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import com.github.alex_moon.porcupi.Config;
import com.github.alex_moon.porcupi.Tellable;
import com.github.alex_moon.porcupi.messages.Message;
import com.github.alex_moon.porcupi.messages.MessageException;
import com.github.alex_moon.porcupi.transport.Transport;

public class ShellClient implements Tellable {
    private String inputLine;
    private String context;

    public ShellClient() {
        Socket socket;
        Transport transport;
        try {
            String action, tokens;
            socket = new Socket("127.0.0.1", Config.managerPort);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            transport = new Transport(socket, this);
            transport.start();
            // @todo use jline - do this properly
            while ((inputLine = stdIn.readLine()) != null) {
                if (!inputLine.trim().equals("")) {
                    if (context != null) {
                        action = context;
                        tokens = inputLine;
                    } else {
                        action = inputLine.split(" ")[0];
                        tokens = "";
                        if (inputLine.split(" ").length > 1) {
                            tokens = inputLine.substring(action.length() + 1);
                        }
                    }
                    try {
                        Message message = new Message(tokens).setAction(action);
                        if (context != null) {
                            message.setContext(context);
                        }
                        transport.tellOut(message);
                    } catch (MessageException e) {
                        System.out.println("Bad command: "  + e.getMessage());
                    }
                }
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

    public void tellIn(Message input) {
        if (input.getContext() != null) {
            if (!input.getContext().equals(context)) {
                System.out.println("now operating in context " + input.getContext());
            }
        } else {
            if (context != null) {
                // @todo not really convinced here - why close the context?
                System.out.println("closing context " + context);
            }
        }
        // @todo surely we can handle multiple contexts
        context = input.getContext();
        System.out.println("received from server: " + input.toString());
    }

    public void tellOut(Message output) {
        // if we're at the end of the chain, this method won't have meaning
        // @todo make Teller abstract and implement a stub?
        System.out.println("HOW DID THIS HAPPEN?: " + output.toString());
    }
}
