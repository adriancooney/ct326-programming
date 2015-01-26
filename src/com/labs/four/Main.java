package com.labs.four;

import java.io.*;

/**
 * Adrian Cooney/12394581
 */
public class Main {
	public static void main(String[] args) {
		// Create the details
		String[] details = {"First Name", "Surname", "Patient ID", "Phone Number"};

		// Create the stdin reader
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

		try {
			// Open up a file
			FileWriter patientData = new FileWriter("patientData.txt");

			// Ask for each of the details, and write them directly to the file
			System.out.println("Please enter patient data.");
			for(String detail : details) {
				System.out.printf("%s: ", detail);
				patientData.write(String.format("%s: %s\n", detail, stdin.readLine()));
			}

			// Close the file
			System.out.println("Patient data saved!\n");
			patientData.close();

			// Now read it int
			System.out.println("Reading patientData.txt");
			new Patient(new FileReader("patientData.txt"));
		} catch (IOException e) {
			System.out.println("IO Error.");
		}
	}
}
