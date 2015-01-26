package com.labs.nine.stock;

import java.util.Random;

/**
 * Adrian Cooney (12394581)
 * 18/11/14 com.labs.nine
 */
public class Main {

    public static Random generator = new Random();
    public static StockRoom room = new StockRoom();

    /**
     * Main entry point for the program.
     */
    public static void main(String[] args) {

        for(int i = 0; i < 10; i++) {
            // Create a couple of annoying attendants and let them at the stock room
            Thread attendant = new Thread(new StockAttendant(room));
            attendant.start();
        }

        try { Thread.sleep(5000); } catch (InterruptedException e) { }

        System.out.println("Closing shop.");
        System.exit(1);

    }

    public static String az = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Generate a simple name for an item.
     * @return
     */
    public static String generateItemName() {
        return String.format("%s%s-%d", az.charAt(rand(0, 26)), az.charAt(rand(0, 26)), rand(0, 240));
    }

    /**
     * Generate a random number between two numbers.
     * @param min
     * @param max
     * @return random int
     */
    public static int rand(int min, int max) {
        return generator.nextInt(max - min) + min;
    }
}
