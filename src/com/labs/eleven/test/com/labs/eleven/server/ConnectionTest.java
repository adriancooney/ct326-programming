package com.labs.eleven.server;

import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import org.apache.commons.io.IOUtils;

import static org.junit.Assert.*;

public class ConnectionTest {
    public static ConnectionDelegate delegate = new ConnectionDelegate() {
        @Override
        void handleUpload(String filename, DataInputStream file) {

        }

        @Override
        void handleDownload(String filename, DataOutputStream output) {

        }

        @Override
        void handleList(DataOutputStream output) {

        }
    };


    @Test
    public void testParseCommand() throws Exception {
        // The input command
        String command = "UPLOAD /path/toFile.txt >>>DATA";

        // Create the input stream
        InputStream in = new ByteArrayInputStream(command.getBytes(Charset.forName("UTF-8")));

        // Create the connection
        Connection connection = new Connection(new Socket(), delegate);

        // Parse the input
        connection.parse(in);
    }
}