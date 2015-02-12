package com.labs.eleven.server;

import com.labs.common.Logger;
import com.labs.eleven.server.command.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Adrian Cooney (12394581)
 * 09/02/15 com.labs.eleven.server
 */
public class Protocol {
    private static Logger logger = new Logger("Protocol");
    private Command command;
    private OutputStream output;

    public void setOutput(OutputStream out) {
        this.output = out;
    }

    /**
     * Handle an incoming command. i.e. Create an instance of the command.
     * @param command
     * @throws ProtocolException
     */
    public void handleCommand(String command) throws ProtocolException {
        logger.log("New command created: %s", command);

        // TODO: Dynamically include all commands
        switch(command) {
            case "LIST": this.command = new List(); break;
            case "UPLOAD": this.command = new Upload(); break;
            case "DOWNLOAD": this.command = new Download(); break;
            default:
                throw new ProtocolException("Unknown command '" + command + "'.");
        }

        this.command.setOutput(this.output);
    }

    /**
     * Handle an incoming argument to the command. i.e. push an argument to the current command
     * @param argument
     * @throws ProtocolException
     */
    public void handleArgument(String argument) throws ProtocolException {
        if(this.command == null) throw new ProtocolException("Cannot push argument to unknown command.");

        logger.log("Pushing argument: %s", argument);
        this.command.pushArgument(argument);
    }

    public void handleStream(InputStream input) throws ProtocolException {
        if(this.command == null) throw new ProtocolException("Cannot push file to unknown command.");

        logger.log("Pushing stream to command.");
        this.command.pushStream(input);
    }

    /**
     * Execute the current command.
     * @throws IOException
     */
    public void execute() throws IOException {
        this.command.execute();
    }

    /**
     * Simple Exception for catching.
     */
    public class ProtocolException extends Exception {
        public ProtocolException() { super(); }
        public ProtocolException(String message) { super(message); }
        public ProtocolException(String message, Throwable cause) { super(message, cause); }
        public ProtocolException(Throwable cause) { super(cause); }
    }

    /**
     * Write data to the output stream.
     * @param output OutputStream
     * @param data String The data to write.
     * @throws IOException
     */
    public static void write(OutputStream output, String data) throws IOException {
        output.write(data.getBytes());
    }

    /**
     * Alias for write.
     * @param output
     * @param data
     * @throws IOException
     */
    public static void send(OutputStream output, String data) throws IOException {
        Protocol.write(output, data);
    }

    /**
     * When a command is successful and does not need to return data, a simple
     * OK message is sent down the line.
     * @param output
     * @throws IOException
     */
    public static void success(OutputStream output) throws IOException {
        Protocol.send(output, "OK");
    }

    /**
     * When a command fails, send a "ERROR" message.
     * @param output
     * @param message
     * @throws IOException
     */
    public static void fail(OutputStream output, String message) throws IOException {
        Protocol.send(output, "ERROR " + message);
    }

    /**
     * Return the current command.
     * @return
     */
    public Command getCommand() {
        return command;
    }
}
