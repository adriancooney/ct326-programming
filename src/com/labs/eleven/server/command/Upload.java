package com.labs.eleven.server.command;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * UPLOAD command to the server. Takes one command.
 *
 * Example:
 *      UPLOAD /file.txt >>>[file contents]
 *
 * Adrian Cooney (12394581)
 * 12/02/15 com.labs.eleven.server.command
 */
public class Upload extends Command {
    public static final String NAME = "UPLOAD";
    public static final String BASE_DIR = "data/";

    public Upload() {
        super(NAME);
    }

    public void execute() throws IOException {
        String filename = this.getFirstArgument();

        if(filename == null) {
            fail("No filename provided.");
            return;
        }

        File file = new File(BASE_DIR + filename);
        logger.log(file.toString());

        FileOutputStream fout = new FileOutputStream(file);
        IOUtils.copy(this.stream, fout);
        fout.close();

        success();

    }
}
