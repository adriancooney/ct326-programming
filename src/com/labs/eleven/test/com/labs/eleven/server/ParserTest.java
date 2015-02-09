package com.labs.eleven.server;

import com.labs.common.Logger;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void testParse() throws Exception {
        // The input command
        String command = "UPLOAD /path/toFile.txt >>>DATA";

        // Create the input stream
        InputStream in = new ByteArrayInputStream(command.getBytes(Charset.forName("UTF-8")));

        Parser parser = new Parser(in, new Parser.Delegate() {
            public void handleToken(String token) {
                Logger.print("Incoming token: %s", token);
            }

            public void handleBytes(byte[] leftovers, InputStream input) {
                Logger.print("Raw data babay!");
            }
        });

        parser.parse();
    }
}