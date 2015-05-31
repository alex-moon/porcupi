package com.github.alex_moon.porcupi.shell;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import jline.console.ConsoleReader;
import jline.console.CursorBuffer;
import jline.console.completer.StringsCompleter;

import com.github.alex_moon.porcupi.Config;
import com.github.alex_moon.porcupi.Tellable;
import com.github.alex_moon.porcupi.messages.Message;
import com.github.alex_moon.porcupi.messages.MessageException;
import com.github.alex_moon.porcupi.transport.Transport;

public class ShellClient implements Tellable {
    private String inputLine;
    private String context;
    private String prompt;
    private ConsoleReader console;
    
    public ShellClient() {
        Socket socket;
        Transport transport;
        try {
            String action, tokens;
            socket = new Socket("127.0.0.1", Config.managerPort);
            transport = new Transport(socket, this);
            transport.start();

            setupConsole();
            while ((inputLine = console.readLine()) != null) {
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
                        console.setPrompt("");
                    } catch (MessageException e) {
                        debug("Bad command: "  + e.getMessage());
                    }
                }
            }
            socket.close();
        } catch (UnknownHostException e) {
            debug("Couldn't find 127.0.0.1...");
            e.printStackTrace();
            System.exit(2);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
        }
    }
    
    private void setPrompt(String prompt) {
        this.prompt = prompt;
        console.setPrompt(prompt);
    }
    
    private void setupConsole() throws IOException {
        console = new ConsoleReader();
        console.setPrompt("porcupi> ");
        console.addCompleter(new StringsCompleter(Message.actions));
    }
    
    private void setContext(String inputContext) {
        context = inputContext;
        if (context != null) {
            console.setPrompt("porcupi:" + context + "> ");
        } else {
            console.setPrompt("porcupi> ");
        }
    }

    public void tellIn(Message input) {
        if (input.getContext() != null) {
            if (!input.getContext().equals(context)) {
                debug("now operating in context " + input.getContext());
            }
        } else {
            if (context != null) {
                // @todo not really convinced here - why close the context?
                debug("closing context " + context);
            }
        }
        // @todo surely we can handle multiple contexts
        setContext(input.getContext());
        debug("received from server: " + input.toString());
        console.setPrompt(prompt);
    }

    public void tellOut(Message output) {
        // if we're at the end of the chain, this method won't have meaning
        // @todo make Teller abstract and implement a stub?
        debug("HOW DID THIS HAPPEN?: " + output.toString());
    }
    
    private void resetConsole() throws IOException {
        CursorBuffer cursorBuffer = console.getCursorBuffer();
        console.resetPromptLine(console.getPrompt(), cursorBuffer.buffer.toString(), cursorBuffer.cursor);
    }
    
    private void debug(String messageString) {
        try {
            console.println("debug: " + messageString);
            resetConsole();
            console.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("CONSOLE IS BROKEN: " + messageString);
            System.exit(2);
        }
    }
}
