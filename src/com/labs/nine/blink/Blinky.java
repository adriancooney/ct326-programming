package com.labs.nine.blink;

import javax.swing.*;

/**
 * Adrian Cooney (12394581)
 * 19/11/14 com.labs.nine.blink
 */
public class Blinky implements Runnable {
    public boolean blinking = true;
    public int sleepTime = 1000;
    public JLabel label;

    public Blinky(JLabel label) {
        this.label = label;
    }

    @Override
    public void run() {
        while(true) {
            // If it's blinking, blink
            if(blinking) label.setVisible(!label.isVisible());
            // Make the thread sleep
            try { Thread.sleep(sleepTime); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
