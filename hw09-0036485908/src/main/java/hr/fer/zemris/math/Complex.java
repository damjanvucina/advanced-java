package hr.fer.zemris.math;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;

public class Complex {
	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);

	private double re;
	private double im;

	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	public double getRe() {
		return re;
	}

	public double getIm() {
		return im;
	}

	public double module() {
		return sqrt(pow(re, 2) + pow(im, 2));
	}

	public Complex multiply(Complex c) {
		Objects.requireNonNull(c, "Other complex number cannot be null.");

		return new Complex(re * c.re - im * c.im, im * c.re + re * c.im);
	}

	public Complex divide(Complex c) {
		Objects.requireNonNull(c, "Other complex number cannot be null.");

		double denominator = pow(c.re, 2) + pow(c.im, 2);
		if (denominator == 0) {
			throw new ArithmeticException("Dividing by zero occured. Enter different complex numbers.");
		}

		double firstNumerator = re * c.re + im * c.im;
		double secondNumerator = im * c.re - re * c.im;

		return new Complex(firstNumerator / denominator, secondNumerator / denominator);
	}

	public Complex add(Complex c) {
		Objects.requireNonNull(c, "Other complex number cannot be null.");

		return new Complex(re + c.re, im + c.im);
	}

	public Complex sub(Complex c) {
		Objects.requireNonNull(c, "Other complex number cannot be null.");

		return new Complex(re - c.re, im - c.im);
	}

	public Complex negate() {
		return new Complex(-re, -im);
	}
	
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Exponent must be non-negative, was:" + n);
		}
		
		double magnitudePowered = pow(module(), n);
		double argument = n * getAngle();

		return new Complex(magnitudePowered * cos(argument), magnitudePowered * sin(argument));
		
	}
	
	private double getAngle() {
		double result = atan2(im, re);
		return (result < 0) ? result + 2 * PI : result;
	}
	
	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Exponent in root caculation must be positive, was: " + n);
		}

		List<Complex> roots = new LinkedList<>();
		double moduleRooted = pow(module(), 1. / n);

		for (int i = 0; i < n; i++) {
			double argument = (getAngle() + 2 * i * PI) / n;
			roots.add(new Complex(moduleRooted * cos(argument), moduleRooted * sin(argument)));
		}
		
		return roots;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		boolean realComponentExists = false;

		if (Double.compare(re, 0.0) != 0) {
			if (re == (int) re) {
				sb.append((int) re);
			} else {
				sb.append(re);
			}
			realComponentExists = true;
		}

		if (Double.compare(im, 0.0) != 0) {
			if (realComponentExists && im > 0) {
				sb.append("+");
			}
			if (im == (int) im) {
				sb.append((int) im);
			} else {
				sb.append(im);
			}
			sb.append("i");
		}

		if (sb.toString().equals("")) {
			return "0";
		} else {
			return sb.toString();
		}
	}
}
