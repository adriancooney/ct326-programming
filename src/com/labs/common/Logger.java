package com.labs.common;

/**
 * Adrian Cooney (12394581)
 * 07/02/15 com.labs.common
 */
public class Logger {
    private String namespace;

    /**
     * Static method to print out string with format. See @String.format
     * @param format
     * @param arguments
     */
    public static void print(String format, Object... arguments) {
        print(String.format(format, arguments));
    }

    /**
     * Print a simple string to stdout.
     * @param message
     */
    public static void print(String message) {
        System.out.println(message);
    }

    /**
     * Create a new logger with a namespace so output is contained in
     * a [<namespace>] much like the node Debug module. For example:
     *
     * @example
     *      Logger server = new Logger("Server");
     *      server.log("Incoming connection!"); // "[Server] Incoming connection!"
     *
     * @param namespace String
     */
    public Logger(String namespace) {
        this.namespace = namespace;
    }

    /**
     * Log a string with format and parameters. See @String.Format.
     * @param format
     * @param arguments
     */
    public void log(String format, Object... arguments) {
        print(getNamespace() + " " + format, arguments);
    }

    /**
     * Log a simple string.
     * @param message
     */
    public void log(String message) {
        print(getNamespace() + " " + message);
    }

    /**
     * Log an exception.
     * @param ex
     */
    public void log(Exception ex) {
        log(ex.toString());
    }

    /**
     * Get the namespace within it's string container.
     * @return
     */
    private String getNamespace() {
        return "[" + namespace + "]";
    }
}
