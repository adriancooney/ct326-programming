package com.labs.six;

import com.labs.three.Rational;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Adrian Cooney (12394581)
 * 22/10/14 com.labs.six
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

        // first print out the variables
        System.out.println("Input: " + rationals);

        // Sort the array with insertion sort
        Rational current, compare; // Initialize the variables
        int n, u;

        // Loop over each item in the array. Anything post `i` index
        // is never touched so it's safe
        for(int i = 1, s = rationals.size(); i < s; i++) {

            // Get the rational at `i`
            current = rationals.get(i);

            // Loop back from `i` all the way to zero
            for(n = i; n > 0; n--) {
                u = n - 1; // Take care for the zeroth index
                compare = rationals.get(u); // Get the rational to compare to at `n`

                // If the rational at `i` is greater than that at `n` and
                // we have travelled more than one index, remove from `i`
                // and insert at n + 1. If we have reached the start of the
                // array, it means it's the smallest so insert at the start
                if(current.compareTo(compare) == 1 || u == 0) {
                    if(i - u > 1) { // Ensure we've travelled more than 1 to swap
                        u = u == 0 ? u : u + 1; // If we've hit the start, insert at 0
                        rationals.remove(i);
                        rationals.add(u, current);
                    }

                    break; // Stop iterating down n and continue with i since element has be sorted
                }
            }
        }

        // Print out the sorted array
        System.out.println("Output: " + rationals);

        // Use internal sorting method to assert sorted correctly
        ArrayList<Rational> sorted = new ArrayList<Rational>(rationals);
        Collections.sort(sorted);

        System.out.println("Sorted correctly? " + (sorted.equals(rationals) ? "Yes." : "No."));
    }
}
