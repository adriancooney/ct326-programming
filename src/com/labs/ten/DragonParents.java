package com.labs.ten;

import java.util.Random;

/**
 * Adrian Cooney (12394581)
 * 19/01/15 com.labs.ten
 */
public class DragonParents implements Runnable {

    public int id;
    public DragonBirthRegister dbr;
    private static Random random = new Random();

    public DragonParents(int id, DragonBirthRegister dbr) {
        this.id = id;
        this.dbr = dbr;
    }

    @Override
    public void run() {
        try {
            // Simulate doing something like going to the dragon birth office etc. idk
            Thread.sleep(500 + random.nextInt(5000));

            // Consume something from the queue
            this.dbr.getName();
        } catch(InterruptedException i) {
            System.out.println("Whoops. Dragons interrupted from sleeping.");
        }

    }
}
