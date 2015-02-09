package com.labs.eleven.server;

import com.labs.common.Logger;
import com.labs.eleven.server.ConnectionDelegate;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Adrian Cooney (12394581)
 * 07/02/15 com.labs.eleven
 */
public class Connection implements Runnable {
    private ConnectionDelegate delegate;
    private Socket connection;
    private DataInputStream in;
    private DataOutputStream out;

    /**
     * Class instantiated upon a new connection to the server. This class
     * is the protocol for the server. See the README.md. Quick synopsis:
     * It accepts one command per request (or connection), executes the
     * command then closes the connection when complete.
     *
     * @param connection The socket instance connected to the server.
     * @param delegate The connection delegate to handle events (such as IOException)
     */
    public Connection(Socket connection, ConnectionDelegate delegate) {
        this.connection = connection;
        this.delegate = delegate;

        try {
            this.in = new DataInputStream(connection.getInputStream());
            this.out = new DataOutputStream(connection.getOutputStream());
        } catch(IOException ex) {
            this.delegate.handleIOException(ex);
        }

        delegate.logger.log("New server connection.");
    }

    @Override
    public void run() {

    }

    /**
     * Parse the incoming request.
     */
    public void parse(InputStream input) throws ProtocolException, IOException {
        // Interesting use of the Scanner on the input stream.
        Scanner data = new Scanner(input);
        Protocol protocol = new Protocol();
        Pattern rawDelimiter = Pattern.compile("^>{3}.*");
        data.useDelimiter(" "); // Split the arguments by space

        // Loop over each
        int i = 0, position = 0; String chunk; boolean consuming = true;
        while(consuming && i++ >= 0) {
            // We have raw bytes, stop the argument pushing and push stream
            if(data.hasNext()) {
                chunk = data.next();

                // Move the character pointer to the current
                position += chunk.length();

                // The first item is the command
                if(i == 1) protocol.handleCommand(chunk);
                // Test of the first three characters to see if they match ">>>" i.e. raw data
                // for some reason, hasNext(pattern) wouldn't work
                else if(rawDelimiter.matcher(chunk).matches()) {
                    position += 3; // Account for the 3 >>>
                    input.
                }
                // Anything thereafter is an argument
                else protocol.handleArgument(chunk);
            } else consuming = false;
        }
    }

    /**
     * The command was a success, close the connection with
     * an `OK`.
     */
    public void success() {

    }

    /**
     * The command failed, close the connection with an
     * `ERROR <message>`.
     */
    public void fail(String message) {

    }

    public class ProtocolException extends Exception {
        public ProtocolException() { super(); }
        public ProtocolException(String message) { super(message); }
        public ProtocolException(String message, Throwable cause) { super(message, cause); }
        public ProtocolException(Throwable cause) { super(cause); }
    }
}
