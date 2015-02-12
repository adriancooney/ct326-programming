package com.labs.eleven.server;

import com.labs.common.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Adrian Cooney (12394581)
 * 07/02/15 com.labs.eleven
 */
public class Server {
    // Server control
    private static final int POOL_SIZE = 10;
    private int port = 8088; // Default to 8088 port

    private ServerSocket socket;
    private ExecutorService pool;

    // Debug
    private Logger logger = new Logger("Server");

    /**
     * Create a new server given a port.
     * @param port
     */
    public Server(int port) {
        this.port = port;

        logger.log("Creating new thread pool of size %d.", POOL_SIZE);
        pool = new Pool();
    }

    /**
     * Start the server.
     * @throws IOException
     */
    public void start() throws IOException {
        logger.log("Starting on port :%d.", this.port);
        this.socket = new ServerSocket(this.port);
        logger.log("Listening on %s:%d", this.socket.getInetAddress(), this.socket.getLocalPort());

        Socket sock; int i = 0;
        while(true && ++i > 0) {
            // Wait for a new connection
            logger.log("Waiting on " + (i > 1 ? "another " : "") + "connection..");
            sock = socket.accept();
            logger.log("New incoming connection.");

            // Create a new Connection instance for each client
            // Submit it to the thread pool. Hopefully there isn't too many clients and they aren't in a rush.
            pool.submit(new Connection(sock, new ConnectionDelegate() {
                @Override
                public void handleIOException(IOException io) {
                    logger.log("IO Exception in the server connection.");
                }
            }));
        }
    }
}
