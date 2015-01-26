package com.labs.ten;

import java.util.ArrayDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Adrian Cooney (12394581)
 * 19/01/15 com.labs.ten
 */
public class DragonBirthRegister {
    public ArrayDeque<String> register = new ArrayDeque<String>();
    private final Lock lock = new ReentrantLock();
    public int maxSize = 0;

    public DragonBirthRegister(int size) {
        this.maxSize = size;
    }

    /**
     * Add a name to the queue.
     * @param name
     */
    public synchronized void addName(String name) {
//        lock.lock(); // Lock this thread

        if(this.maxSize > 0) { // If it has a max size, make sure it's not exceeding
            if(this.register.size() >= maxSize) {
                try { // If the actual size is equal to the max
                    System.out.println(String.format("[!] Queue full! Waiting on space. Postponing adding '%s' on thread [%s].", name, Thread.currentThread().getName()));

                    while (this.register.size() >= maxSize)
                        wait(); // Wait for space on the register

                    System.out.println(String.format("[+] Item in queue for thread [%s].", Thread.currentThread().getName()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(String.format("[%s] -> %s", Thread.currentThread().getName(), name));

        // Add to the register
        register.add(name);

        prettyPrintQueue();

        notifyAll(); // Notify any threads waiting on data that there is some in the queue on the current object

//        lock.unlock(); // Unlock

    }

    /**
     * Get a name from the queue.
     * @return
     */
    public synchronized String getName() {
//        lock.lock(); // Lock the thread, pull out the name from the register

        if(this.register.size() <= 0) { // If there's no items in the queue, wait for items
            try {
                System.out.println(String.format("[!] Queue empty! Waiting on data. Holding thread [%s] up.", Thread.currentThread().getName()));

                while(this.register.size() <= 0)
                    wait();

                System.out.println(String.format("[+] Item pushed into queue for thread [%s].", Thread.currentThread().getName()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Grab some data from the queue
        String name = register.poll();

        System.out.println(String.format("[%s] <- %s", Thread.currentThread().getName(), name));
        prettyPrintQueue();

        // Notify that there is space in the queue
        notifyAll();

//        lock.unlock();

        return name;
    }

    /**
     * Pretty print the queue with stars and brackets.
     *
     * e.g. [********-----------]
     */
    public void prettyPrintQueue() {
        String log = "[";
        int size = this.register.size();
        int width = maxSize > 0 ? maxSize : 20;

        for(int i = 0; i < width; i++)
            log += i < size ? "*" : "-";

        log += "]";

        System.out.println(log);
    }
}
