package com.labs.eleven.server.command;

import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * DOWNLOAD a file from the server.
 *
 * Adrian Cooney (12394581)
 * 12/02/15 com.labs.eleven.server.command
 */
public class Download extends Command {
    public static final String NAME = "DOWNLOAD";
    public static final String BASE_DIR = "data/";

    public Download() {
        super(NAME);
    }

    @Override
    public void execute() throws IOException {
        String filename = getFirstArgument();

        if(filename == null) {
            fail("Please specify the file name.");
            return;
        }

        File file = new File(BASE_DIR + "." + filename);

        try {
            FileInputStream fin = new FileInputStream(file);

            IOUtils.copy(fin, output);
        } catch(FileNotFoundException e) {
            fail("File not found.");
            return;
        }
    }
}
