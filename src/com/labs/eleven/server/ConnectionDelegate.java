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

    // Error handling
    public void handleIOException(IOException ex) {
        ex.printStackTrace();
    }

    // Error handling
    public void handleProtocolException(Protocol.ProtocolException ex) {
        ex.printStackTrace();
    }
}
