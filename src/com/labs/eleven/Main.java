package com.labs.eleven;

import com.labs.common.Logger;
import com.labs.eleven.client.Client;
import com.labs.eleven.server.Server;

import java.io.IOException;

/**
 * Adrian Cooney (12394581)
 * 07/02/15 com.labs.eleven
 */
public class Main {
    public static final String HOST = "0.0.0.0";
    public static final int PORT = 8089;
    public static Logger logger = new Logger("Main");

    public static void main(String[] args) {
        logger.log("Creating new TCP server in it's own thread.");

        // Disable parser output
        Logger.disable("Parser");

        // Create our server in it's own thread
        Thread server = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Start the server listening on PORT
                    (new Server(PORT)).start();
                } catch (IOException e) {
                    logger.log(e);
                }
            }
        });

        logger.log("Creating a new client.");
        Thread client = new Thread(new Runnable() {
            @Override
            public void run() {
                // Create a new client
                new Client(HOST, PORT);
            }
        });

        client.start();
        server.start();
    }
}
