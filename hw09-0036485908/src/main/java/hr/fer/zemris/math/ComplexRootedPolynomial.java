package hr.fer.zemris.math;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ComplexRootedPolynomial {

	List<Complex> roots;

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

	public Complex apply(Complex z) {
		Objects.requireNonNull(z, "Given complex number cannot be null.");

		Complex result = z.sub(roots.get(0));
		for (int i = 1, size = roots.size(); i < size; i++) {
			result = result.multiply(z.sub(roots.get(i)));
		}

		return result;
	}

	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial explicitForm = new ComplexPolynomial(roots.get(0), Complex.ONE);

		for (int i = 1, size = roots.size(); i < size; i++) {
			explicitForm = explicitForm.multiply(new ComplexPolynomial(roots.get(i).negate(), Complex.ONE));
		}
		
		return explicitForm;
	}

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
