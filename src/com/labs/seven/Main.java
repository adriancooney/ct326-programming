package com.labs.seven;

import com.labs.three.Rational;

import java.util.*;

/**
 * Adrian Cooney (12394581)
 * 29/10/2014 com.labs.seven
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<Rational> rationals = new ArrayList<Rational>();

        // Create the new rational array
        try {
            rationals.addAll(Arrays.asList(
                new Rational(2, 5),
                new Rational(3, 5),
                new Rational(4, 5),
                new Rational(7, 10),
                new Rational(3, 10),
                new Rational(3, 10),
                new Rational(8, 15),
                new Rational(13, 15),
                new Rational(4, 7),
                new Rational(1, 7),
                new Rational(2, 3)
            ));
        } catch (Exception e) {
            System.out.println("Invalid rational: " + e.getMessage());
            System.exit(1);
        }

        // Shuffle the list
        Collections.shuffle(rationals);

        // Sort them using Collection.sort
        // which is passed an anonymous custom comparator
        Collections.sort(rationals, new Comparator<Rational>() {
            @Override
            public int compare(Rational rational, Rational rational2) {
                return rational.compareTo(rational2);
            }
        });

        try {
            // Print out the rationals
            System.out.println("Rationals: " + rationals);

            // Read in the rational
            Rational input = readRational();

            // Find where the input is in the list
            int index = Collections.binarySearch(rationals, input);

            // If the index is greater than 0, it is contained in the list
            if(index > 0) {
                System.out.printf("Rational is at #%d index in the list.", index);
            } else {
                System.out.printf("Rational is not in the list.");
            }
        } catch (Exception e) {
            System.out.println("Invalid rational: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Read in a rational (fraction) from the stdin.
     * @return Rational
     * @throws Exception if invalid Rational
     */
    public static Rational readRational() throws Exception {
        Scanner stdin = new Scanner(System.in);

        System.out.println("Please input rational.");
        System.out.print("Numerator (p): ");
        int numerator = stdin.nextInt();
        System.out.print("Denominator (q): ");
        int denominator = stdin.nextInt();

        Rational input = new Rational(numerator, denominator);
        System.out.println(input);

        return input;
    }
}
