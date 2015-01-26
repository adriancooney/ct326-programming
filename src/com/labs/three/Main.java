package com.labs.three;

import java.util.*;

/**
 * Created by Adrian on 03/10/2014.
 *
 * This program takes in an input e.g. 1/2 + 3/4 * 5/6
 * and outputs the value of the equation (?).
 *
 * It uses the stack data structure and has two data types;
 * rational and operations (+-/*). Whenever an operation is
 * encountered, it is set as the currentOperation. Whenever
 * a rational is encountered, it is pushed to the stack. If
 * there is a currentOperation and two rationals in the stack,
 * the rationals are popped from the stack, the currentOperation
 * is applied to the two and the value is pushed back onto the
 * stack. Rinse and repeat.
 *
 * WARNING: This calculator does not respect the rules of precedence!
 * (That's probably getting a little complicated for this assignment)
 *
 * Example:
 * <-   1/2 + 2/3 - 1/5 * 5/6 / 7/8 							(Input)
 * ->   Input: 1/2 + 2/3 - 1/5 * 5/6 / 7/8 						(Output)
 *		Parsed rational <1/2>. Pushing to stack.
 *		Discovered operation '+'. Setting as current operation.
 *		Parsed rational <2/3>. Pushing to stack.
 *		Current operation '+' ongoing. Running.
 *		Popped <2/3> and <1/2> from the stack for operation.
 *		Output from '+' operation: <7/6>
 *		Pushing <7/6> onto stack and resetting current operation.
 *		Discovered operation '-'. Setting as current operation.
 *		Parsed rational <1/5>. Pushing to stack.
 *		Current operation '-' ongoing. Running.
 *		Popped <1/5> and <7/6> from the stack for operation.
 *		Output from '-' operation: <29/30>
 *		Pushing <29/30> onto stack and resetting current operation.
 *		Discovered operation '*'. Setting as current operation.
 *		Parsed rational <5/6>. Pushing to stack.
 *		Current operation '*' ongoing. Running.
 *		Popped <5/6> and <29/30> from the stack for operation.
 *		Output from '*' operation: <29/36>
 *		Pushing <29/36> onto stack and resetting current operation.
 *		Discovered operation '/'. Setting as current operation.
 *		Parsed rational <7/8>. Pushing to stack.
 *		Current operation '/' ongoing. Running.
 *		Popped <7/8> and <29/36> from the stack for operation.
 *		Output from '/' operation: <58/63>
 *		Pushing <58/63> onto stack and resetting current operation.
 *		Output from input: <58/63>
 *
 * NOTE:
 * The above input is the equivalent to ((((1/2) + (2/3)) - (1/5)) * (5/6)) / (7/8)
 * if you want to put it into an actual calculator (which respects precedence)
 * to check the answer.
 *
 * @lab 3
 */
public class Main {
	public static void main(String args[]) {
		// The possible operations. Their position in the string
		// corresponds to their identity in the switch where
		// the operation is run. This should have been setup into
		// a class, but for simplicities sake, it's here.
		String ops = "+-*/";

		// Print out the input
		String input = "";
		for(String arg : args) input += arg + " ";
		System.out.println("Input: " + input);

		// Create the rational stack and the current operation
		Stack<Rational> rationals = new Stack<Rational>();
		int currentOperation = -1; // -1 for no operation

		// Loop over all the arguments
		for(String arg : args) {

			// Test if its an operation or a rational
			if(arg.length() == 1 && ops.contains(arg)) {
				currentOperation = ops.indexOf(arg);
				System.out.println("Discovered operation '" + arg + "'. Setting as current operation.");
			} else {
				// I understand the assignment asked to use StringTokenizer but the docs
				// says it's deprecated in favor of .split (which is intrinsically the same
				// thing)
				String[] pq = arg.split("/");

				try {
					// Create the rational and push it to the stack
					Rational r = new Rational(Integer.parseInt(pq[0]), Integer.parseInt(pq[1]));
					rationals.push(r);

					System.out.println("Parsed rational " + r + ". Pushing to stack.");
				} catch(Exception e) {
					// Error! Print and exit.
					System.out.println(e);
					System.exit(0);
				}

				// Check to see if there's an ongoing operation
				// If so, pop the last two elements in the stack
				// since each operation only takes two numbers
				// the push the result to the stack
				if(currentOperation != -1) {
					System.out.println("Current operation '" + ops.charAt(currentOperation) + "' ongoing. Running.");
					Rational a, b, c;

					a = rationals.pop();
					b = rationals.pop();

					System.out.println("Popped " + a + " and " + b + " from the stack for operation.");

					try {
						// Run the operation (add/sub, multiply/divide)
						switch (currentOperation) {
							case 0: c = b.add(a); break;
							case 1: c = b.sub(a); break;
							case 2: c = b.mult(a); break;
							case 3: c = b.div(a); break;
							default:
								throw new Exception("Unknown operation.");
						}

						System.out.println("Output from '" + ops.charAt(currentOperation) + "' operation: " + c);
						System.out.println("Pushing " + c + " onto stack and resetting current operation.");
						rationals.push(c);
					} catch(Exception e) {
						System.out.println(e);
						System.exit(0);
					}

					// Reset the operation
					currentOperation = -1;
				}
			}
		}

		System.out.println("Output from input: " + rationals.pop());
	}
}
