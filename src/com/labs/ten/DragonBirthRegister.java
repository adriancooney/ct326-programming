package com.labs.ten;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Adrian Cooney (12394581)
 * 19/01/15 com.labs.ten
 */
public class DragonBirthRegister {
    public ArrayDeque<String> register = new ArrayDeque<String>();
    private final Lock lock = new ReentrantLock();

    public void addName(String name) {
        lock.lock(); // Lock the thread, add to the register

        register.add(name);
        prettyPrintSize();

        lock.unlock(); // Unlock
    }

    public String getName() {
        lock.lock(); // Lock the thread, pull out the name from the register

        String name = register.poll();
        prettyPrintSize();

        lock.unlock();

        return name;
    }

    public void prettyPrintSize() {
        String log = "[";
        int size = this.register.size();
        int width = 40;

        for(int i = 0; i < width; i++)
            log += i < size ? "*" : "-";

        log += "]";

        System.out.print(String.format("%s ", log));
    }

    @Override
    public String toString() {
        return String.format("DragonBirthRegister[names count = %d]", register.size());
    }
}
