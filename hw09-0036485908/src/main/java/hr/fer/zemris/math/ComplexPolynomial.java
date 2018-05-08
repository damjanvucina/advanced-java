package hr.fer.zemris.math;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ComplexPolynomial {

	List<Complex> factors;

	public ComplexPolynomial(Complex... factors) {
		Objects.requireNonNull(factors, "This complex polynomial's factors cannot be null.");
		this.factors = new LinkedList<>();

		for (Complex factor : factors) {
			this.factors.add(factor);
		}
	}

	public short order() {
			return (short) (factors.size() - 1);
	}

	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Objects.requireNonNull(p, "Given complex polynomial cannot be null.");

		Complex[] productFactors = new Complex[factors.size() + p.factors.size() - 1];
		productFactors = multiplicationSetUp(productFactors.length);
		
		for (int i = 0, firstSize = factors.size(); i < firstSize; i++) {
			for (int j = 0, resultIndex = i+j, secondSize = p.factors.size(); j < secondSize; j++) {
				productFactors[resultIndex] = productFactors[resultIndex].add(factors.get(i).multiply(p.factors.get(j)));
			}
		}

		return new ComplexPolynomial(productFactors);
	}

	private Complex[] multiplicationSetUp(int length) {
		Complex[] initialized = new Complex[length];
		
		for(int i =0; i< length; i++) {
			initialized[i] = Complex.ZERO;
		}
		
		return initialized;
	}

	public ComplexPolynomial derive() {
		Objects.requireNonNull(factors, "This polyonimal's factors cannot be null.");

		Complex derivative[] = new Complex[factors.size() - 1];
		for (int i = 0, length = derivative.length, factorsSize = factors.size(); i < length; i++) {
			derivative[i] = factors.get(i).multiply(new Complex(factorsSize - 1 - i, 0));
		}

		return new ComplexPolynomial(derivative);
	}

	public Complex apply(Complex z) {
		Objects.requireNonNull(z, "Given complex polynomial cannot be null.");

		int currentPower = factors.size() - 1;
		Complex result = factors.get(0).multiply(z.power(currentPower--));

		for (int i = 1, size = factors.size(); i < size; i++) {
			result = result.add(factors.get(i).multiply(z.power(currentPower--)));
		}

		return result;
	}

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
