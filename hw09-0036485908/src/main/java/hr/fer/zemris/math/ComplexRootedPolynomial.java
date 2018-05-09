package hr.fer.zemris.math;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

// TODO: Auto-generated Javadoc
/**
 * The Class ComplexRootedPolynomial.
 */
public class ComplexRootedPolynomial {

	/** The roots. */
	List<Complex> roots;

	/**
	 * Instantiates a new complex rooted polynomial.
	 *
	 * @param roots the roots
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
	 * Apply.
	 *
	 * @param z the z
	 * @return the complex
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
	 * To complex polynom.
	 *
	 * @return the complex polynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial explicitForm = new ComplexPolynomial(roots.get(0), Complex.ONE);

		for (int i = 1, size = roots.size(); i < size; i++) {
			explicitForm = explicitForm.multiply(new ComplexPolynomial(roots.get(i).negate(), Complex.ONE));
		}
		
		return explicitForm;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
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
	 * Index of closest root for.
	 *
	 * @param z the z
	 * @param treshold the treshold
	 * @return the int
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
