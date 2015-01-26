package com.labs.ten;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Adrian Cooney (12394581)
 * 19/01/15 com.labs.ten
 *
 * DragonBabyNamer -- A handy class to help name you newborn baby
 * dragons! (i.e. producer)
 */
public class DragonBabyNamer implements Runnable {
    // Data courtesy of https://github.com/kraihn/dragon-names
    public static String dragonData = "data/dragons.txt";

    // Hold the dragonNames in an array
    public String[] dragonNames;
    public Random random;
    public DragonBirthRegister dbr;
    public int id;

    public DragonBabyNamer(int id, DragonBirthRegister dbr) {
        this.dragonNames = getDragonNames();
        this.random = new Random();
        this.id = id;
        this.dbr = dbr;
    }

    /**
     * Get a dragon baby name at random.
     * @return String
     */
    public String getDragonName() {
        return this.dragonNames[this.random.nextInt(this.dragonNames.length)];
    }

    /**
     * Convert the baby namer to a string (i.e. print all names)
     * @return String
     */
    public String toString() {
        return "Dragons" + Arrays.toString(this.dragonNames);
    }

    /**
     * Import the dragon names from the data file.
     * @return String[] dragon names
     */
    public static String[] getDragonNames() {
        ArrayList<String> dragons = new ArrayList<String>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(dragonData));
            for(String line; (line = br.readLine()) != null; ) {
                dragons.add(line);
            }

            br.close();
        } catch(IOException e) {
            System.out.println("Error getting dragon names!");
        }

        String[] dragonsArray = new String[dragons.size()];
        return dragons.toArray(dragonsArray);
    }

    @Override
    public void run() {
        try { Thread.sleep(200); } catch(InterruptedException i) { System.out.println("Namer interrupted."); }

        String name = this.getDragonName();
        this.dbr.addName(name);
//        System.out.println(String.format("[producer:#%d:%s] -> %s", this.id, Thread.currentThread().getName(), name));
    }
}
