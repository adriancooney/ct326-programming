package com.labs.eleven.server;

import com.labs.common.Logger;
import com.labs.eleven.server.command.Command;
import com.labs.eleven.server.command.List;

/**
 * Adrian Cooney (12394581)
 * 09/02/15 com.labs.eleven.server
 */
public class Protocol {
    private static Logger logger = new Logger("Protocol");

    public void handleCommand(String command) {
        logger.log("New command created: %s", command);
    }

    public void handleArgument(String argument) {
        logger.log("Pushing argument: %s", argument);
    }
}
