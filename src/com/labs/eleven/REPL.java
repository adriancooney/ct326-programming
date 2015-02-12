package com.labs.eleven;

import com.labs.eleven.server.Connection;
import com.labs.eleven.server.Parser;
import com.labs.eleven.server.Protocol;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Adrian Cooney (12394581)
 * 09/02/15 com.labs.eleven
 */
public class REPL {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("> ");
        String input;
        while((input = scanner.nextLine()) != null) {

            try {
                // Parse the request
                Parser.parse(new ByteArrayInputStream(input.getBytes()), System.out).execute();
            } catch (Protocol.ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.print("\n> ");
        }
    }
}
