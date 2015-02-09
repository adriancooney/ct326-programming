package com.labs.eleven.server;

import com.labs.common.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Adrian Cooney (12394581)
 * 09/02/15 com.labs.eleven
 */
public abstract class ConnectionDelegate {
    public Logger logger = new Logger("Connection");

    // Error handling
    public void handleIOException(IOException ex) {
        logger.log(ex);
    }

    // Input to the server
    abstract void handleUpload(String filename, DataInputStream file);

    // Output from the server
    abstract void handleDownload(String filename, DataOutputStream output);
    abstract void handleList(DataOutputStream output);
}
