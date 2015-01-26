package com.labs.one;

// Abstract base class Employee.

import java.util.Calendar;
import java.util.Date;

public abstract class Employee {

    private String firstName;
    private String lastName;
	public Date joinDate;
	private int id;
	private static int employeeCount = 0;

    // constructor
	public Employee(String first, String last) {
		this.firstName = first;
		this.lastName = last;
		this.id = ++employeeCount;
	}

	public Employee(String first, String last, Date joinDate) throws Exception{
		this(first, last);
		this.setJoinDate(joinDate);
	}

    // get first name
    public String getFirstName() {
        return firstName;
    }

    // get last name
    public String getLastName() {
        return lastName;
    }

    public String toString() {
        return firstName + ' ' + lastName;
    }

	public Date setJoinDate(Date joinDate) throws Exception {
		// So much code for one date..
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 1990);
		cal.set(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		if(joinDate.after(new Date()) || joinDate.before(cal.getTime())) throw new Exception(String.format("Cannot set invalid join date (%s) for employee '%s %s'", joinDate.toString(), this.firstName, this.lastName));
		return (this.joinDate = joinDate);
	}

    public abstract double earnings();
}
