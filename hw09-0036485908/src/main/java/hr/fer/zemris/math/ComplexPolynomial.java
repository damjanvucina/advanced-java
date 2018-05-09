package hr.fer.zemris.math;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

// TODO: Auto-generated Javadoc
/**
 * The Class ComplexPolynomial.
 */
public class ComplexPolynomial {

	/** The factors. */
	List<Complex> factors;

	/**
	 * Instantiates a new complex polynomial.
	 *
	 * @param factors the factors
	 */
	public ComplexPolynomial(Complex... factors) {
		Objects.requireNonNull(factors, "This complex polynomial's factors cannot be null.");
		this.factors = new LinkedList<>();

		for (Complex factor : factors) {
			this.factors.add(factor);
		}
	}

	/**
	 * Order.
	 *
	 * @return the short
	 */
	public short order() {
		return (short) (factors.size() - 1);
	}

	/**
	 * Multiply.
	 *
	 * @param p the p
	 * @return the complex polynomial
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Objects.requireNonNull(p, "Given complex polynomial cannot be null.");

		Complex[] productFactors = new Complex[factors.size() + p.factors.size() - 1];
		productFactors = multiplicationSetUp(productFactors.length);

		for (int i = 0, firstSize = factors.size(); i < firstSize; i++) {
			for (int j = 0, resultIndex = i + j, secondSize = p.factors.size(); j < secondSize; j++) {
				productFactors[resultIndex] = productFactors[resultIndex]
						.add(factors.get(i).multiply(p.factors.get(j)));
			}
		}

		return new ComplexPolynomial(productFactors);
	}

	/**
	 * Multiplication set up.
	 *
	 * @param length the length
	 * @return the complex[]
	 */
	private Complex[] multiplicationSetUp(int length) {
		Complex[] initialized = new Complex[length];

		for (int i = 0; i < length; i++) {
			initialized[i] = Complex.ZERO;
		}

		return initialized;
	}

	/**
	 * Derive.
	 *
	 * @return the complex polynomial
	 */
	public ComplexPolynomial derive() {
		Objects.requireNonNull(factors, "This polyonimal's factors cannot be null.");

		Complex derivative[] = new Complex[factors.size() - 1];
		for (int i = 0, length = derivative.length, factorsSize = factors.size(); i < length; i++) {
			derivative[i] = factors.get(i).multiply(new Complex(factorsSize - 1 - i, 0));
		}

		return new ComplexPolynomial(derivative);
	}

	/**
	 * Apply.
	 *
	 * @param z the z
	 * @return the complex
	 */
	public Complex apply(Complex z) {
		Objects.requireNonNull(z, "Given complex polynomial cannot be null.");

		int currentPower = factors.size() - 1;
		Complex result = factors.get(0).multiply(z.power(currentPower--));

		for (int i = 1, size = factors.size(); i < size; i++) {
			result = result.add(factors.get(i).multiply(z.power(currentPower--)));
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("f(z) = ");

		if (factors.size() == 0) {
			sb.append("0");
		} else {

			for (int i = factors.size() - 1; i >= 0; i--) {
				sb.append("(").append(factors.get(i)).append(")");
				sb.append(i > 0 ? "z" : "");
				sb.append(i > 1 ? "^" + String.valueOf(i) : "");
				sb.append("+");
			}
			sb.delete(sb.lastIndexOf("+"), sb.length());
		}

		return sb.toString();
	}
}
