package com.labs.eleven.server.command;

import com.labs.common.Logger;
import com.labs.eleven.server.Connection;
import com.labs.eleven.server.Protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Adrian Cooney (12394581)
 * 09/02/15 com.labs.eleven.server.command
 */
public abstract class Command {
    public String name;
    private int argumentCount = 0;
    private ArrayList<String> arguments;
    protected Logger logger;
    protected OutputStream output;
    protected InputStream stream;

    public abstract void execute() throws IOException;

    public Command(String name) {
        logger = new Logger("Command->" + name);
        arguments = new ArrayList<>();
        setName(name);
    }

    /**
     * Set the output stream from the command.
     * @param output
     */
    public void setOutput(OutputStream output) {
        this.output = output;
    }

    /**
     * Set the name of the command.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Push an argument to the command.
     * @param argument
     */
    public void pushArgument(String argument) {
        arguments.add(argument);
        argumentCount++;
    }

    /**
     * Push the stream to the command.
     * @param stream
     */
    public void pushStream(InputStream stream) {
        this.stream = stream;
    }

    /** Argument getters **/
    public String getFirstArgument() { return getArgument(0); }
    public String getSecondArgument() { return getArgument(1); }
    public String getThirdArgument() { return getArgument(2); }
    public String getFourthArgument() { return getArgument(3); }

    /**
     * Get an argument at an index.
     * @param index
     * @return
     */
    public String getArgument(int index) {
        if(index <= argumentCount) return arguments.get(index);
        else return null;
    }

    /**
     * Protocol operation was a success.
     * @throws IOException
     */
    public void success() throws IOException {
        Protocol.success(output);
    }

    /**
     * Protocol operation failed.
     * @param message
     * @throws IOException
     */
    public void fail(String message) throws IOException {
        Protocol.fail(output, message);
    }

    /**
     * Send data down to the socket/client.
     * @param data
     * @throws IOException
     */
    public void send(String data) throws IOException {
        Protocol.send(output, data);
    }
}
