package com.labs.ten;

/**
 * Adrian Cooney (12394581)
 * 19/01/15 com.labs.ten
 */
public class DragonParents implements Runnable {

    public int id;
    public DragonBirthRegister dbr;

    public DragonParents(int id, DragonBirthRegister dbr) {
        this.id = id;
        this.dbr = dbr;
    }

    @Override
    public void run() {
        String name = this.dbr.getName(); // Consume something from the queue
        System.out.println(String.format("[consumer:#%d:%s] <- %s", this.id, Thread.currentThread().getName(), name));

        // Wait a while before getting preggers again
        try { Thread.sleep(500); } catch(InterruptedException i) { System.out.println("Whoops. Dragons interrupted from sleeping."); }
    }
}
