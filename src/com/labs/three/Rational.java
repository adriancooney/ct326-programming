package com.labs.three;

import java.math.BigInteger;

/**
 * Created by Adrian on 03/10/2014.
 */
public class Rational implements Comparable<Rational> {
	private int p;
	private int q;

    /**
	 * Create a new Rational.
	 * @param p The numerator.
	 * @param q The denominator.
	 * @throws Exception if denominator = 0
	 */
	public Rational(int p, int q) throws Exception {
		this.setP(p);
		this.setQ(q);
	}

	/*
	 * Getter for P and Q.
	 */
	public int getP() { return p; } // Numerator
	public int getQ() { return q; } // Denominator

	/*
	 * Setters for P and Q.
	 */
	public Rational setP(int p) { this.p = p; return this; }

	public Rational setQ(int q) throws Exception {
		// Ensure the denominator is not zero.
		if(q == 0) throw new Exception("The denominator (Q) cannot be zero.");
		this.q = q;
		return this;
	}

    /**
     * Return the decimal value of the rational.
     * @return P/Q
     */
    public double toDecimal() {
        return ((double)this.getP())/((double)this.getQ());
    }

	/*
	 * The different operations.
	 */
	public Rational add(Rational r) throws Exception {
		return (new Rational((this.getP() * r.getQ()) + (this.getQ() * r.getP()), this.getQ() * r.getQ())).rationalize();
	}

	public Rational sub(Rational r) throws Exception {
		return this.add(((Rational) r.clone()).setP(r.getP() * -1));
	}

	public Rational mult(Rational r) throws Exception {
		return (new Rational(this.getP() * r.getP(), this.getQ() * r.getQ())).rationalize();
	}

	public Rational div(Rational r) throws Exception {
		return this.mult(new Rational(r.getQ(), r.getP()));
	}

	/**
	 * Return the greatest common devisor between two
	 * integers
	 * @param a int
	 * @param b int
	 * @return int
	 */
	public static int gcd(int a, int b) {
		// http://stackoverflow.com/questions/4009198/java-get-greatest-common-divisor
		return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
	}

	/**
	 * Rationalize a fraction to it's smallest form.
	 * @return Rational (self, not new instance)
	 * @throws Exception
	 */
	public Rational rationalize() throws Exception {
		int gcd = Rational.gcd(this.getP(), this.getQ());
		this.setP(this.getP() / gcd);
		this.setQ(this.getQ() / gcd);
		return this;
	}

	/**
	 * Convert the fraction to a string.
	 * @return
	 */
	@Override
	public String toString() {
		return String.format("<%d/%d = %.2f>", this.getP(), this.getQ(), this.toDecimal());
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		try {
			return new Rational(this.getP(), this.getQ());
		} catch (Exception e) {
			throw new CloneNotSupportedException();
		}
	}

    @Override
    public int compareTo(Rational rational) {
        double c = this.toDecimal() - rational.toDecimal();
        return c > 0 ? 1 : (c < 0 ? -1 : 0);
    }
}
