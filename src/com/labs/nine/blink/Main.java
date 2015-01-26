package com.labs.nine.blink;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;

/**
 * Adrian Cooney (12394581)
 * 19/11/14 com.labs.nine.blink
 */
public class Main {
    public static JLabel label;

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Simple GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add the label
        label = new JLabel("blink", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(300, 100));
        frame.getContentPane().add(label, BorderLayout.CENTER);

        // Show the frame
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        // Create our blinky
        final Blinky onTheBlink = new Blinky(label);

        // Get the sleep time
        onTheBlink.sleepTime = getSleepTime();

        // On click, toggle
        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {}
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {}
            @Override
            public void mouseEntered(MouseEvent mouseEvent) {}
            @Override
            public void mouseExited(MouseEvent mouseEvent) {}
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                onTheBlink.blinking = !onTheBlink.blinking;
            }
        });

        // Start the blinker
        Thread blinker = new Thread(onTheBlink);
        blinker.start();
    }

    /**
     * Get sleep time from input.
     * @return
     */
    public static int getSleepTime() {
        System.out.println("Welcome to the Incredible Window Blinker System (IWBS).");
        System.out.println("(Exercise caution, may induce seizures)");
        System.out.print("Please enter a blink speed: ");

        // Open up the input stream
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        // Parse the sleep time
        int sleepTime = Integer.parseInt(input);

        if(sleepTime < 10) {
            System.out.println("Sleep time too short, you won't see it. Setting to 1000ms.");
            sleepTime = 1000;
        }

        // close the input stream
        scanner.close();

        return sleepTime;
    }
}
