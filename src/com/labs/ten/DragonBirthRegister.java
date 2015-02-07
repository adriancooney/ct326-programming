package com.labs.ten;

import java.util.ArrayDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Adrian Cooney (12394581)
 * 19/01/15 com.labs.ten
 */
public class DragonBirthRegister {
    public ArrayDeque<String> register = new ArrayDeque<String>();
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public int maxSize = 0;

    public DragonBirthRegister(int size) {
        this.maxSize = size;
    }

    /**
     * Add a name to the queue.
     * @param name
     */
    public void addName(String name) throws InterruptedException {
        try {
            // Attempt to get the lock
            lock.lock();

            if (this.maxSize > 0) { // If it has a max size, make sure it's not exceeding
                if (this.register.size() >= maxSize) {// If the actual size is equal to the max
                    System.out.println(String.format("[!] Queue full! Waiting on space. Postponing addition of '%s' to queue on thread [%s].", name, Thread.currentThread().getName()));

                    while (this.register.size() >= maxSize)
                        notFull.await(); // Wait for space on the register (i.e. wait for it to be "notFull")

                    System.out.println(String.format("[+] Item in queue for thread [%s].", Thread.currentThread().getName()));
                }
            }


            // Add to the register
            register.add(name);

            // Print out some stats
            System.out.println(String.format("%s << \"%s\" [%s]", prettyPrintQueue(), name, Thread.currentThread().getName()));

            // Notify any consumers waiting on content
            notEmpty.signal();

        } finally {
            // Unlock regardless of any error
            lock.unlock();
        }
    }

    /**
     * Get a name from the queue.
     * @return
     */
    public String getName() throws InterruptedException {

        try {
            lock.lock(); // Lock the thread, pull out the name from the register

            if (this.register.size() <= 0) { // If there's no items in the queue, wait for items
                System.out.println(String.format("[!] Queue empty! Waiting on data. Holding thread [%s] up.", Thread.currentThread().getName()));

                // Constantly check to see if there is data in the register
                while (this.register.size() <= 0)
                    notEmpty.await(); // And wait on it being "notEmpty"

                System.out.println(String.format("[+] Item pushed into queue, consuming in thread [%s].", Thread.currentThread().getName()));
            }

            // Grab some data from the queue
            String name;
            if(Main.CURRENT_PART == Main.Parts.ONE) name = register.pollFirst();
            else name = register.pollLast();

            // Print out some stats
            System.out.println(String.format("%s >> \"%s\" [%s]", prettyPrintQueue(), name, Thread.currentThread().getName()));


            // Notify that there is space in the queue
            notFull.signal();

            return name;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Pretty print the queue with stars and brackets.
     *
     * e.g. [********-----------]
     *
     * @param width The width of the outputted queue
     */
    public String prettyPrintQueue(int width) {
        String log = "[";
        int size = this.register.size();
        width = maxSize > 0 ? maxSize : width;

        for(int i = 0; i < width; i++)
            log += i < size ? "*" : "-";

        return log + (maxSize <= 0 ? ":::" : "") + "]";
    }

    public String prettyPrintQueue() { return prettyPrintQueue(20); }
}
