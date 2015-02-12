package com.labs.eleven.server;

import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;

public class ParserTest {
    @Test
    public void testParseCommand() throws Exception {
        // The input command
        String command = "LIST /path/toFile.txt >>>DATA";

        // Create the input stream
        InputStream in = new ByteArrayInputStream(command.getBytes(Charset.forName("UTF-8")));

        // Parse the input
        Parser.parse(in, System.out);
    }
}