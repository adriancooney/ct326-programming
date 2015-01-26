package com.labs.four;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * Adrian Cooney/12394581
 */
public class Patient {
	public Patient(FileReader file) {

		// Open up the file reader
		LineNumberReader lnr = new LineNumberReader(file);
		String line;

		try {
			System.out.println("-- Patient data (from file)");
			// Loop over each line and print with the line number
			while((line = lnr.readLine()) != null) {
				System.out.println(String.format("%d: %s", lnr.getLineNumber(), line));
			}
		} catch (IOException e) {
			System.out.println("IO Error.");
		}
	}
}
