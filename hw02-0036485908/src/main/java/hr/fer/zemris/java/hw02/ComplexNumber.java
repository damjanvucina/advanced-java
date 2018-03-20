package hr.fer.zemris.java.hw02;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;
import static java.lang.Math.atan2;
import static java.lang.Math.PI;;

public class ComplexNumber {

	private double real;
	private double imaginary;

	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	public double getReal() {
		return real;
	}

	public double getImaginary() {
		return imaginary;
	}

	public double getMagnitude() {
		return sqrt(pow(real, 2) + pow(imaginary, 2));
	}

	public double getAngle() {
		double result = atan2(imaginary, real);
		return (result < 0) ? result + 2 * PI : result;

	}

	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * cos(angle), magnitude * sin(angle));
	}

	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(real + c.real, imaginary + c.imaginary);
	}

	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(real - c.real, imaginary - c.imaginary);
	}

	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(real * c.real - imaginary * c.imaginary, imaginary * c.real + real * c.imaginary);
	}

	public ComplexNumber div(ComplexNumber c) {
		double denominator = pow(c.real, 2) + pow(c.imaginary, 2);

		if (denominator == 0) {
			throw new ArithmeticException("Dividing by zero occured. Enter different complex numbers.");
		}

		double firstNumerator = real * c.real + imaginary * c.imaginary;
		double secondNumerator = imaginary * c.real - real * c.imaginary;

		return new ComplexNumber(firstNumerator / denominator, secondNumerator / denominator);
	}

	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Exponent in power calculation cannot be negative, was: " + n);
		}

		double magnitudePowered = pow(getMagnitude(), n);
		double argument = n * getAngle();

		return new ComplexNumber(magnitudePowered * cos(argument), magnitudePowered * sin(argument));
	}

	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("Exponent in root caculation must be positive, was: " + n);
		}

		ComplexNumber[] roots = new ComplexNumber[n];
		double magnitudeRooted = pow(getMagnitude(), 1 / n);

		for (int i = 0; i < n; i++) {
			double argument = (getAngle() + 2 * i * PI) / n;
			roots[i] = new ComplexNumber(magnitudeRooted * cos(argument), magnitudeRooted * sin(argument));
		}

		return roots;
	}
	
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

			if (s.charAt(s.length() - 2) == '0') {
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (Double.compare(real, 0.0) != 0) {
			if (real == (int) real) {
				sb.append((int) real);
			} else {
				sb.append(real);
			}

		}

		if (Double.compare(imaginary, 0.0) != 0) {
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

	public static void main(String[] args) {

		ComplexNumber c = parse("1.311-3.10i");
		System.out.println("realni dio je " + c.real);
		System.out.println("kompleksni dio je " + c.imaginary);
		System.out.println(c.toString());
	}

}
