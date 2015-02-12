package com.labs.eleven.server.command;

import com.labs.common.Logger;
import com.labs.eleven.server.Connection;
import com.labs.eleven.server.Protocol;

import java.io.IOException;
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

    public abstract void execute() throws IOException;

    public Command(String name) {
        logger = new Logger("Command->" + name);
        arguments = new ArrayList<>();
        setName(name);
    }

    public void setOutput(OutputStream output) {
        this.output = output;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void pushArgument(String argument) {
        arguments.add(argument);
        argumentCount++;
    }

    public String getFirstArgument() { return getArgument(0); }
    public String getSecondArgument() { return getArgument(1); }
    public String getThirdArgument() { return getArgument(2); }
    public String getFourthArgument() { return getArgument(3); }

    public String getArgument(int index) {
        if(index <= argumentCount) return arguments.get(index);
        else return null;
    }

    public void success() throws IOException {
        Protocol.success(output);
    }

    public void fail() throws IOException {
        Protocol.success(output);
    }

    public void send(String data) throws IOException {
        Protocol.send(output, data);
    }
}
