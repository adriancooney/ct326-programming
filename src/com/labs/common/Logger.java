package com.labs.common;

import java.util.regex.Pattern;

/**
 * Adrian Cooney (12394581)
 * 07/02/15 com.labs.common
 */
public class Logger {
    private String namespace;
    private static Pattern enabled;

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
     * Print with a namespace if enabled with format.
     * @param namespace
     * @param format
     * @param arguments
     */
    public static void print(String namespace, String format, Object... arguments) {
        if(enabled.matcher(namespace).matches())
            print(formatNamespace(namespace) + " " + format, arguments);
    }

    /**
     * Print with a namespace if enabled
     * @param namespace
     * @param message
     */
    public static void print(String namespace, String message) {
        if(enabled.matcher(namespace).matches())
            print(formatNamespace(namespace) + " " + message);
    }

    /**
     * Enable different namespaces for logging.
     * @param pattern
     */
    public static void enable(String pattern) {
        enabled = Pattern.compile(pattern);
    }

    /**
     * Format the namespace.
     * @param namespace
     * @return
     */
    public static String formatNamespace(String namespace) {
        return "[" + namespace + "]";
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
        print(namespace, format, arguments);
    }

    /**
     * Log a simple string.
     * @param message
     */
    public void log(String message) {
        print(namespace, message);
    }

    /**
     * Log an exception.
     * @param ex
     */
    public void log(Exception ex) {
        log(ex.toString());
    }
}
