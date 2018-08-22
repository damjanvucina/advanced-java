package hr.fer.zemris.java.hw16.jvdraw.geometry;

import static java.lang.Math.min;

import java.awt.Point;
import java.awt.Rectangle;
import static java.lang.Math.abs;

public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	// private int topLeftX;
	// private int topLeftY;
	// private int width;
	// private int height;
	private Rectangle boundingRectangle;

	@Override
	public void visit(Line line) {
		int topLeftX = min(line.getStartPoint().x, line.getEndPoint().x);
		int topLeftY = min(line.getStartPoint().y, line.getEndPoint().y);
		int width = abs(line.getStartPoint().x - line.getEndPoint().x);
		int height = abs(line.getStartPoint().y - line.getEndPoint().y);

		Rectangle lineRectangle = new Rectangle(topLeftX, topLeftY, width, height);
		updateBoundingRectangle(lineRectangle);
	}

	private void updateBoundingRectangle(Rectangle objectRectangle) {
		if (boundingRectangle == null) {
			boundingRectangle = objectRectangle;

		} else {
			boundingRectangle = boundingRectangle.union(objectRectangle);
		}
	}

	@Override
	public void visit(Circle circle) {
		int radius = circle.calculateRadius();
		Point center = circle.getCenter();
		
		int topLeftX = center.x - radius;
		int topLeftY = center.y - radius;
		int diameter = 2*radius;
		
		Rectangle circleRectangle = new Rectangle(topLeftX, topLeftY, diameter, diameter);
		updateBoundingRectangle(circleRectangle);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		visit((Circle) filledCircle);
	}

	public Rectangle getBoundingBox() {
		return boundingRectangle;
	}
	
	public void resetBoundingRectangle() {
		boundingRectangle = null;
	}

}
