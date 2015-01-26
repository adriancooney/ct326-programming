package com.labs.one;

import com.sun.xml.internal.fastinfoset.algorithm.HexadecimalEncodingAlgorithm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by adrian on 18/09/2014.
 */
public class Payroll {
	private ArrayList<Employee> employees = new ArrayList<Employee>();

	/**
	 * Add an employee to the payroll.
	 * @param employee
	 * @return
	 */
	public Employee addEmployee(Employee employee) {
		this.employees.add(employee);
		return employee;
	}

	/**
	 * Process the payroll.
	 */
	public void payout() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -5);
		Date fiveYearsAgo = cal.getTime();

		double pay = 0, total = 0;
		boolean bonus = false;
		for(Employee employee : this.employees) {
			pay = employee.earnings();

			if(employee.joinDate != null && employee.joinDate.before(fiveYearsAgo)) {
				pay += 100;
				bonus = true;
			}

			System.out.println(String.format("Paying out $" + pay + " to %s %s. " + (bonus == true ? "(+bonus)" : ""), employee.getFirstName(), employee.getLastName()));

			// Reset the pay
			total += pay; pay = 0; bonus = false;
		}

		System.out.println("Total payout: $" + total);
	}
}
