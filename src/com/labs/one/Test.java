package com.labs.one;
// Driver for Employee hierarchy

// Java core packages
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

// Java extension packages
import javax.swing.JOptionPane;

public class Test {

    // test Employee hierarchy
    public static void main(String args[]) {
        Employee employee; // superclass reference
        String output = "";

        Boss boss = new Boss("John", "Smith", 800.0);

        CommissionWorker commissionWorker =
                new CommissionWorker(
                "Sue", "Jones", 400.0, 3.0, 150);

        PieceWorker pieceWorker =
                new PieceWorker("Bob", "Lewis", 2.5, 200);

        HourlyWorker hourlyWorker =
                new HourlyWorker("Karen", "Price", 13.75, 40);

        DecimalFormat precision2 = new DecimalFormat("0.00");

// Employee reference to a Boss
        employee = boss;

        output += employee.toString() + " earned $"
                + precision2.format(employee.earnings()) + "\n"
                + boss.toString() + " earned $"
                + precision2.format(boss.earnings()) + "\n";

        // Employee reference to a CommissionWorker
        employee = commissionWorker;

        output += employee.toString() + " earned $"
                + precision2.format(employee.earnings()) + "\n"
                + commissionWorker.toString() + " earned $"
                + precision2.format(
                commissionWorker.earnings()) + "\n";

        // Employee reference to a PieceWorker
        employee = pieceWorker;

        output += employee.toString() + " earned $"
                + precision2.format(employee.earnings()) + "\n"
                + pieceWorker.toString() + " earned $"
                + precision2.format(pieceWorker.earnings()) + "\n";

		// Employee reference to an HourlyWorker
        employee = hourlyWorker;

        output += employee.toString() + " earned $"
                + precision2.format(employee.earnings()) + "\n"
                + hourlyWorker.toString() + " earned $"
                + precision2.format(hourlyWorker.earnings()) + "\n";

//        JOptionPane.showMessageDialog(null, output,
//                "Demonstrating Polymorphism",
//                JOptionPane.INFORMATION_MESSAGE);

		// Give them join dates
		DateFormat simple = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);

		try {
			pieceWorker.setJoinDate(simple.parse("August 14, 2000"));
			commissionWorker.setJoinDate(simple.parse("October 12, 2001"));
			boss.setJoinDate(simple.parse("May 10, 1974")); // Throws exception for before 1990
			hourlyWorker.setJoinDate(simple.parse("June 10, 2015")); // Throws exception for date in future (when above exception is fixed)
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		// Create a new payroll
		Payroll payroll = new Payroll();

		// Add the employees
		payroll.addEmployee(boss);
		payroll.addEmployee(pieceWorker);
		payroll.addEmployee(commissionWorker);
		payroll.addEmployee(hourlyWorker);

		// And payout..
		payroll.payout();

        System.exit(0);
    }
} // end class Test
