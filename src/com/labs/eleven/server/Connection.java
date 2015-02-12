package com.labs.eleven.server;

import com.labs.common.Logger;
import com.labs.eleven.server.ConnectionDelegate;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Adrian Cooney (12394581)
 * 07/02/15 com.labs.eleven
 */
public class Connection implements Runnable {
    private Logger logger;
    private ConnectionDelegate delegate;
    private Socket connection;
    private InputStream in;
    private OutputStream out;
    private static int connections = 0;

    /**
     * Class instantiated upon a new connection to the server. This class
     * is the protocol for the server. See the README.md. Quick synopsis:
     * It accepts one command per request (or connection), executes the
     * command then closes the connection when complete.
     *
     * @param connection The socket instance connected to the server.
     * @param delegate The connection delegate to handle events (such as IOException)
     */
    public Connection(Socket connection, ConnectionDelegate delegate) throws IOException {
        this.logger = new Logger("Connection-" + ++connections);
        this.connection = connection;
        this.delegate = delegate;
        this.in = connection.getInputStream();
        this.out = connection.getOutputStream();

        logger.log("New server connection.");
    }

    @Override
    public void run() {
        try {
            // Start the request parsing in it's own thread
            // to allow for more connections
            parse(in, out);
        } catch (Protocol.ProtocolException e) {
            delegate.handleProtocolException(e);
        } catch (IOException e) {
            delegate.handleIOException(e);
        }
    }

    /**
     * Parse and execute the incoming request.
     */
    public void parse(InputStream input, OutputStream out) throws Protocol.ProtocolException, IOException {
        logger.log("Parsing incoming request.");

        Parser parser = new Parser();

        // Parse the request
        Protocol request = parser.parse(input, out);

        logger.log("Executing request.");

        // Execute the request (or command) however the parser decided/parsed.
        request.execute();

        logger.log("Request complete. Closing connection.");

        // Close the connection
        out.close(); connections--;
    }
}
