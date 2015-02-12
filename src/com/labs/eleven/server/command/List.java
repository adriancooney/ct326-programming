package com.labs.eleven.server.command;

import java.io.IOException;

/**
 * Adrian Cooney (12394581)
 * 09/02/15 com.labs.eleven.server.command
 */
public class List extends Command {
    public static final String NAME = "LIST";
    public static final String BASE_DIR = "data/";

    public List() {
        super(NAME);
    }

    public void execute() throws IOException {
        this.logger.log("LISTING!");

        this.send(this.listDir());
    }

    /**
     * List the directory and return a string.
     *
     * @param separator The string separator between paths.
     * @return String
     * @throws IOException
     */
    public String listDir(String separator) throws IOException {
        return "/data/" + separator +
                "/data/data.txt" + separator +
                "/data/archive";
    }

    /**
     * List a dir using default separator.
     * @return String
     * @throws IOException
     */
    public String listDir() throws IOException {
        return listDir("\n");
    }
}
