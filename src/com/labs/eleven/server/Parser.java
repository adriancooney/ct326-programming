package com.labs.eleven.server;

import com.labs.common.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

/**
 * Adrian Cooney (12394581)
 * 09/02/15 com.labs.eleven.server
 */
public class Parser {
    public static Logger logger = new Logger("Parser");
    public static final String END_SEQUENCE = "<<<>>>";

    /**
     * Parse an incoming request and build a Protocol. The first entry, separated
     * by spaces, is the command. Anything thereafter is pushed as an argument.
     * @param input InputStream
     * @param out OutputStream
     * @return Protocol
     * @throws Protocol.ProtocolException
     * @throws IOException
     */
    public static Protocol parse(InputStream input, OutputStream out) throws Protocol.ProtocolException, IOException {
        logger.log("Parsing incoming request.");

        // Interesting use of the Scanner on the input stream.
        Scanner data = new Scanner(input);
        data.useDelimiter(" "); // Split the arguments by space

        // Create the protocol handler
        Protocol protocol = new Protocol();
        protocol.setOutput(out);

        // Loop over each
        int i = 0; String chunk;
        while(i++ >= 0) {
            // We have raw bytes, stop the argument pushing and push stream
            if(data.hasNext()) {
                chunk = data.next();
                logger.log("Handling chunk: %s", chunk);

                if(chunk.equals(END_SEQUENCE)) return protocol;

                // The first item is the command
                if(i == 1) protocol.handleCommand(chunk);
                    // Anything thereafter is an argument
                else protocol.handleArgument(chunk);
            } else {
                return protocol;
            }
        }

        return null;
    }
}
