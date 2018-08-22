package hr.fer.zemris.java.hw16.jvdraw.geometry;

// TODO: Auto-generated Javadoc
/**
 * The Interface GeometricalObjectVisitor.
 */
public interface GeometricalObjectVisitor {
	
	/**
	 * Visit.
	 *
	 * @param line the line
	 */
	void visit(Line line);

	/**
	 * Visit.
	 *
	 * @param circle the circle
	 */
	void visit(Circle circle);

	/**
	 * Visit.
	 *
	 * @param filledCircle the filled circle
	 */
	void visit(FilledCircle filledCircle);
}
