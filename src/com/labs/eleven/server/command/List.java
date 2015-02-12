package com.labs.eleven.server.command;

import com.labs.common.Filesystem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * LIST a file directory.
 *
 * Example:
 *
 *      LIST
 *
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
        ArrayList<String> paths = Filesystem.gatherFiles((new File(BASE_DIR)).listFiles());
        String output = "";
        for(String path : paths) output += path + separator;
        return output;
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
