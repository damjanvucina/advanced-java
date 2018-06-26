package hr.fer.zemris.java.hw16.jvdraw.geometry;

public interface GeometricalObjectVisitor {
	void visit(Line line);

	void visit(Circle circle);

	void visit(FilledCircle filledCircle);
}
