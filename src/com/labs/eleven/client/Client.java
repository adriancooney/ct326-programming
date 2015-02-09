package com.labs.eleven.client;

import com.labs.common.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;

/**
 * Adrian Cooney (12394581)
 * 07/02/15 com.labs.eleven
 */
public class Client extends JFrame {
    // GUI control
    Dimension screenSize = getToolkit().getScreenSize();

    // Socket control
    private int port;
    private String host;
    private Socket socket;

    // Debug
    private Logger logger = new Logger("Client");

    public Client(String host, int port) {
        this.port = port;
        this.host = host;

        setTitle("Socky");
        setLocation((screenSize.width / 2), (screenSize.height / 2));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        try {
            connect();
        } catch (IOException e) {
            logger.log(e);
        }
    }

    /**
     * Connect the client to the socket.
     * @throws IOException
     */
    private void connect() throws IOException {
        logger.log("Connecting to /%s:%d.", host, port);
        this.socket = new Socket(host, port);
        logger.log("Connected to %s:%d.", socket.getInetAddress(), socket.getPort());
    }

    /**
     * Create the UI.
     */
    private void scaffold() {

    }
}
