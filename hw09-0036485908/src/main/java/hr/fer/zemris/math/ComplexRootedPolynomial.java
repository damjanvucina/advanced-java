package hr.fer.zemris.math;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * The class that represents a mathematical polynomial with complex factors in
 * form f(z) = (z-z1)*(z-z2)*...*(z-zn), z(i) being intersections with abscissa.
 * It provides user with method for calculating the value of the polynomial in
 * the specified abscissa coordinate, a method for transforming into regular
 * (non-root-form) polynomial and a method for closest root calculation.
 * 
 * @author Damjan Vučina
 */
public class ComplexRootedPolynomial {

	/** The roots of this Complex polynomial. */
	List<Complex> roots;

	/**
	 * Instantiates a new complex rooted polynomial.
	 *
	 * @param roots
	 *            the roots of this Complex polynomial.
	 * @throws NullPointerException
	 *             if the roots provided are null
	 * @throw IllegalArgumentException if zero roots are provided
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		Objects.requireNonNull(roots, "This complex polynomial roots cannot be null.");
		if (roots.length == 0) {
			throw new IllegalArgumentException(
					"You must provide at least a single root, roots provided:" + roots.length);
		}

		this.roots = new LinkedList<>();

		for (Complex root : roots) {
			this.roots.add(root);
		}
	}

	/**
	 * Calculates the value of the polynomial in the specified abscissa coordinate
	 *
	 * @param z
	 *            the z complex number defining the abscissa coordinate
	 * @return the complex number calculated as the value of the polynomial in the
	 *         specified abscissa coordinate
	 */
	public Complex apply(Complex z) {
		Objects.requireNonNull(z, "Given complex number cannot be null.");

		Complex result = Complex.ONE;
		for (int i = 0, size = roots.size(); i < size; i++) {
			result = result.multiply(z.sub(roots.get(i)));
		}

		return result;
	}

	/**
	 * Transforms this ComplexRootedPolynomial into regular (non-root) form
	 * polynomial.
	 *
	 * @return the complex polynomial in regular (non-root) form
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial explicitForm = new ComplexPolynomial(roots.get(0), Complex.ONE);

		for (int i = 1, size = roots.size(); i < size; i++) {
			explicitForm = explicitForm.multiply(new ComplexPolynomial(roots.get(i).negate(), Complex.ONE));
		}

		return explicitForm;
	}

	/**
	 * Prints a String representation of this ComplexRootedPolynomial to the
	 * console.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("f(z) = ");

		for (Complex root : roots) {
			sb.append("(z-(").append(root).append("))*");
		}
		sb.delete(sb.lastIndexOf("*"), sb.length());

		return sb.toString();
	}

	/**
	 * Calculates the index of closest root for the for given complex number z that
	 * is within treshold; if there is no such returns -1.
	 *
	 * @param z
	 *            the complex number whose roots are inspected
	 * @param treshold
	 *            the treshold defining the distance between the roots
	 * @return the indef of the closest root or -1 if there is none within the treshold
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		Objects.requireNonNull(z, "Given complex number cannot be null.");

		int closesRootIndex = -1;
		double currentDistance;

		double minimumDistance = z.sub(roots.get(0)).module();
		closesRootIndex = 0;

		for (int i = 1, size = roots.size(); i < size; i++) {
			currentDistance = z.sub(roots.get(i)).module();

			if (currentDistance < minimumDistance) {
				minimumDistance = currentDistance;
				closesRootIndex = i;
			}
		}
		return closesRootIndex;
	}
}
