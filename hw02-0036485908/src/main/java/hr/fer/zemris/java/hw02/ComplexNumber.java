package hr.fer.zemris.java.hw02;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;
import static java.lang.Math.atan2;
import static java.lang.Math.PI;;

/**
 * The class which represents an unmodifiable complex number and provides and
 * implements basic methods for operations like adding, subtracting,
 * multiplying, dividing, calculating roots etc.
 * 
 * @author Damjan Vučina
 * @version 1.0
 */
public class ComplexNumber {

	/** The real component of the complex number */
	private double real;

	/** The imaginary component of the complex number. */
	private double imaginary;

	/**
	 * Instantiates a new complex number and initalizes it based on user's input.
	 *
	 * @param real
	 *            The real component of the complex number
	 * @param imaginary
	 *            The imaginary component of the complex number.
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Gets the real component of the complex number
	 *
	 * @return the real component of the complex number
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Gets the imaginary component of the complex number
	 *
	 * @return the imaginary component of the complex number
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Method that gets the magnitude of the complex number.
	 *
	 * @return the magnitude of the complex number.
	 */
	public double getMagnitude() {
		return sqrt(pow(real, 2) + pow(imaginary, 2));
	}

	/**
	 * Gets the angle of the complex number.
	 *
	 * @return the angle of the complex number.
	 */
	public double getAngle() {
		double result = atan2(imaginary, real);
		return (result < 0) ? result + 2 * PI : result;

	}

	/**
	 * Factory method that instantiates a new complex number and initializes it
	 * based on user's input of the real component of the complex number.
	 *
	 * @param real
	 *            the real component of the complex number
	 * @return the instantiated complex number
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Factory method that instantiates a new complex number and initializes it
	 * based on user's input of the imaginary component of the complex number.
	 *
	 * @param imaginary
	 *            the imaginary component of the complex number
	 * @return the instantiated complex number
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Factory method that instantiates a new complex number and initializes it
	 * based on user's input of the magnitude and angle of the complex number.
	 *
	 * @param magnitude
	 *            the magnitude of the complex number
	 * @param angle
	 *            the angle of the complex number
	 * @return the instantiated complex number
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * cos(angle), magnitude * sin(angle));
	}

	/**
	 * Method that is used for adding two complex numbers
	 *
	 * @param c
	 *            the complex number provided as the argument
	 * @return the complex number which is the result of the operation
	 * @throws IllegalArgumentException
	 *             if the complex number provided in the argument is null
	 */
	public ComplexNumber add(ComplexNumber c) {
		if (c == null) {
			throw new IllegalArgumentException("Complex number provided in the argument cannot be null");
		}
		return new ComplexNumber(real + c.real, imaginary + c.imaginary);
	}

	/**
	 * Method that is used for subtracting two complex numbers
	 *
	 * @param c
	 *            the complex number provided as the argument
	 * @return the complex number which is the result of the operation
	 * @throws IllegalArgumentException
	 *             if the complex number provided in the argument is null
	 */
	public ComplexNumber sub(ComplexNumber c) {
		if (c == null) {
			throw new IllegalArgumentException("Complex number provided in the argument cannot be null");
		}
		return new ComplexNumber(real - c.real, imaginary - c.imaginary);
	}

	/**
	 * Method that is used for multiplying two complex numbers
	 *
	 * @param c
	 *            the complex number provided as the argument
	 * @return the complex number which is the result of the operation
	 * @throws IllegalArgumentException
	 *             if the complex number provided in the argument is null
	 */
	public ComplexNumber mul(ComplexNumber c) {
		if (c == null) {
			throw new IllegalArgumentException("Complex number provided in the argument cannot be null");
		}
		return new ComplexNumber(real * c.real - imaginary * c.imaginary, imaginary * c.real + real * c.imaginary);
	}

	/**
	 * Method that is used for dividing two complex numbers
	 *
	 * @param c
	 *            the complex number provided as the argument
	 * @return the complex number which is the result of the operation
	 * @throws IllegalArgumentException
	 *             if the complex number provided in the argument is null
	 */
	public ComplexNumber div(ComplexNumber c) {
		if (c == null) {
			throw new IllegalArgumentException("Complex number provided in the argument cannot be null");
		}
		double denominator = pow(c.real, 2) + pow(c.imaginary, 2);

		if (denominator == 0) {
			throw new ArithmeticException("Dividing by zero occured. Enter different complex numbers.");
		}

		double firstNumerator = real * c.real + imaginary * c.imaginary;
		double secondNumerator = imaginary * c.real - real * c.imaginary;

		return new ComplexNumber(firstNumerator / denominator, secondNumerator / denominator);
	}

	/**
	 * Method that is used for calculating the power of the complex number. Power
	 * must not be negative.
	 *
	 * @param n
	 *            the power of the complex number that is to be calculated
	 * @return the calculated complex number
	 * @throws IllegalArgumentException
	 *             if the exponent in power calculation is negative
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Exponent in power calculation cannot be negative, was: " + n);
		}

		double magnitudePowered = pow(getMagnitude(), n);
		double argument = n * getAngle();

		return new ComplexNumber(magnitudePowered * cos(argument), magnitudePowered * sin(argument));
	}

	/**
	 * Method that is used for calculating roots of the complex number. Root must be
	 * positive. must not be negative.
	 *
	 * @param n
	 *            the number of roots of the complex number that are to be
	 *            calculated
	 * @return the calculated roots of the complex number[]
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Exponent in root caculation must be positive, was: " + n);
		}

		ComplexNumber[] roots = new ComplexNumber[n];
		double magnitudeRooted = pow(getMagnitude(), 1. / n);

		for (int i = 0; i < n; i++) {
			double argument = (getAngle() + 2 * i * PI) / n;
			roots[i] = new ComplexNumber(magnitudeRooted * cos(argument), magnitudeRooted * sin(argument));
		}
		return roots;
	}

	/**
	 * Parses the complex number as a string input and returns its ComplexNumber
	 * representation.
	 *
	 * @param s
	 *            string representation of the complex number
	 * 
	 * @return the ComplexNumber representation of the complex number
	 */
	public static ComplexNumber parse(String s) {
		if (s == null) {
			return null;
		}

		if (s.equals("0") || s.equals("0i")) {
			return new ComplexNumber(0, 0);
		}

		if (s.equals("i")) {
			return new ComplexNumber(0, 1);
		}

		if (s.endsWith("i") && (s.charAt(s.length() - 2) != '0')) { // postoji imaginarni dio
			s = s.substring(0, s.length() - 1); // odrizi i

			int numOfOperators = 0;

			for (char c : s.toCharArray()) {
				if (c == '+' || c == '-') {
					numOfOperators++;
				}
			}

			if (numOfOperators == 0) {
				return new ComplexNumber(0, Double.parseDouble(s));

			} else if (numOfOperators == 1) { // prvi pozitivan ili prvi ne postoji
				int indexOfOperator = s.indexOf("+");
				if (indexOfOperator == -1) {
					indexOfOperator = s.indexOf("-");
				}
				if (indexOfOperator == 0) {

					return new ComplexNumber(0, Double.parseDouble(s));
				} else {
					String firstArgument = s.substring(0, indexOfOperator);
					String secondArgument = s.substring(indexOfOperator);

					return new ComplexNumber(Double.parseDouble(firstArgument), Double.parseDouble(secondArgument));
				}
			} else { // dva operatora
				int indexPlus = s.lastIndexOf("+");
				int indexMinus = s.lastIndexOf("-");
				int separatorIndex = (indexPlus > indexMinus) ? indexPlus : indexMinus;

				String firstArgument = s.substring(0, separatorIndex);
				String secondArgument = s.substring(separatorIndex);

				return new ComplexNumber(Double.parseDouble(firstArgument), Double.parseDouble(secondArgument));
			}
		}

		else { // nepostoji imaginarni dio

			if (!s.contains("+") && !s.contains("-")) {
				return new ComplexNumber(Double.parseDouble(s), 0);
			}

			else if (s.charAt(s.length() - 2) == '0') {
				s = s.substring(0, s.lastIndexOf('0') - 1);
			}

			try {
				return new ComplexNumber(Double.parseDouble(s), 0);
			} catch (NumberFormatException exc) {
				System.out.println("Invalid format. Cannot parse given string.");
			}
		}
		return null;
	}

	/**
	 * Formatted complex number in format real_component + imaginary_component
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		boolean realComponentExists = false;

		if (Double.compare(real, 0.0) != 0) {
			if (real == (int) real) {
				sb.append((int) real);
			} else {
				sb.append(real);
			}
			realComponentExists = true;
		}

		if (Double.compare(imaginary, 0.0) != 0) {
			if (realComponentExists && imaginary > 0) {
				sb.append("+");
			}
			if (imaginary == (int) imaginary) {
				sb.append((int) imaginary);
			} else {
				sb.append(imaginary);
			}
			sb.append("i");
		}

		if (sb.toString().equals("")) {
			return "0";
		} else {
			return sb.toString();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imaginary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Method that checks if two complex numbers are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComplexNumber other = (ComplexNumber) obj;
		if (Double.doubleToLongBits(imaginary) != Double.doubleToLongBits(other.imaginary))
			return false;
		if (Double.doubleToLongBits(real) != Double.doubleToLongBits(other.real))
			return false;
		return true;
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1];
		System.out.println(c3);
		
	}

}
