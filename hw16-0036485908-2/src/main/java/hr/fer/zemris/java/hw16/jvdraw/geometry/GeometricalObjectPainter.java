package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Graphics2D;

public class GeometricalObjectPainter implements GeometricalObjectVisitor{
	
	private Graphics2D g2d;

	public GeometricalObjectPainter() {
	}

	public Graphics2D getG2d() {
		return g2d;
	}

	public void setG2d(Graphics2D g2d) {
		this.g2d = g2d;
	}

	@Override
	public void visit(Line line) {
		line.paint(g2d);
	}

	@Override
	public void visit(Circle circle) {
		circle.paint(g2d);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		filledCircle.paint(g2d);
	}
	
	

}
