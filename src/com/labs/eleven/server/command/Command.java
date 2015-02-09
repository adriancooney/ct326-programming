package com.labs.eleven.server.command;

import java.util.ArrayList;

/**
 * Adrian Cooney (12394581)
 * 09/02/15 com.labs.eleven.server.command
 */
public abstract class Command {
    public String name;
    private int argumentCount = 0;
    private ArrayList<String> arguments;

    public abstract void run();

    public Command(String name) {
        setName(name);
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
}
