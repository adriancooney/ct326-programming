package com.labs.eleven.server;

import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;

public class ParserTest {

    @Test
    public void testHasFileDelimiter() throws Exception {
        assert Parser.hasDelimiter(new byte[] { 'A', 's', '>', '>', '>' }, 4);
        assert Parser.hasDelimiter(new byte[] { 'a', 's', 'd', 'f', 'u', 'y', 'A', 's', '>', '>', '>' }, 10);
        assert Parser.hasDelimiter(new byte[] { 'a', 's', 'd', 'f', 'u', 'y', 'A', 's', '>', '>', '>' });
        assert !Parser.hasDelimiter(new byte[] { 'A', 's' }, 0);
        assert !Parser.hasDelimiter(new byte[] { 'A', 's', '>', '>', 'F' }, 4);
    }

    @Test
    public void testParseCommand() throws Exception {
        Parser parser = new Parser();

        // The input command
        String command = "LIST /path/toFile.txt >>>DATA";

        // Create the input stream
        InputStream in = new ByteArrayInputStream(command.getBytes(Charset.forName("UTF-8")));

        // Parse the input
        parser.parse(in, System.out);
    }

    @Test
    public void testParseCommandLong() throws Exception {
        Parser parser = new Parser();

        // The input command
        String command = "LIST /path/toFileasdfoinyasodasldfhnals;kdfhn;oaslkdfn;laskhfn;lkashfnl;kasdfnkahsndfoahklsdf;nkas;fhksafhdifynaopisdfnyopaisfnyopaisydfnopiasyfnopaisyfnpoaisydfn.txt >>>DATA";

        // Create the input stream
        InputStream in = new ByteArrayInputStream(command.getBytes(Charset.forName("UTF-8")));

        // Parse the input
        parser.parse(in, System.out);
    }
}