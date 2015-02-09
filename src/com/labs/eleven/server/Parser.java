package com.labs.eleven.server;

import com.labs.common.Logger;
import com.sun.tools.doclets.internal.toolkit.util.DocFinder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Adrian Cooney (12394581)
 * 09/02/15 com.labs.eleven.server
 */
public class Parser {
    private static final char TOKEN_DELIMITER = ' ';
    private static final char[] BYTE_DELIMITER = new char[] { '>', '>', '>'};
    private static final int READ_COUNT = 100;
    private static enum STATES {
        TOKEN, BYTES, NONE
    }

    private Delegate delegate;
    private InputStream input;

    private STATES state = STATES.NONE;
    private byte[] currentToken = new byte[400];
    private int currentTokenPointer = 0;
    private int currentRawByteDelimiterPointer = 0;


    public Parser(InputStream input, Delegate delegate) {
        this.input = input;
        this.delegate = delegate;
    }

    public void parse() throws IOException {
        InputStream input = this.input;
        int i = 0, n = 0;
        byte[] data = new byte[READ_COUNT];
        byte current;
        boolean consuming = true;

        // If there is input
        while(input.available() > 0 && consuming) {
            // Read in 100 bytes (or whatever READ_COUNT is)
            int byteCount = input.read(data, 0, READ_COUNT);

            // Loop over the bytes returned
            for(i = 0; i < byteCount && consuming; i++, n++) {
                // Get the current point
                current = data[i];

                // If this is the very first byte and it's not a delimiter, then set it to the token
                if(n == 0 && current != TOKEN_DELIMITER) state = STATES.TOKEN;

                Logger.print("i = %d, n = %d, current = %d, state = %s, currentRawByteDelimiterPointer = %d", i, n, current, state.name(), currentRawByteDelimiterPointer);
                if(current == TOKEN_DELIMITER) {
                    Logger.print("TOKEN DELIMITER!");
                    if (state == STATES.NONE) {
                        state = STATES.TOKEN;
                        currentTokenPointer = 0;
                    } else if (state == STATES.TOKEN) {
                        state = STATES.NONE;
                        delegate.handleToken(new String(currentToken));
                    } else {
                        currentToken[currentTokenPointer++] = current;
                    }
                } else if(current == BYTE_DELIMITER[currentRawByteDelimiterPointer]) {
                    currentRawByteDelimiterPointer++;

                    if(currentRawByteDelimiterPointer == BYTE_DELIMITER.length) {
                        consuming = false;
                        delegate.handleBytes(data, input);
                    }
                } else {
                    currentRawByteDelimiterPointer = 0;
                }
            }
        }
    }

    public abstract static class Delegate {
        public abstract void handleToken(String token);
        public abstract void handleBytes(byte[] leftovers, InputStream bytes);
    }
}
