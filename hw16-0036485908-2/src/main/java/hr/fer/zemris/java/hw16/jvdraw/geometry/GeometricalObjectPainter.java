package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class GeometricalObjectPainter implements GeometricalObjectVisitor {

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
		g2d.setColor(line.getFgColorProvider().getCurrentColor());
		g2d.drawLine(line.getStartPoint().x, line.getStartPoint().y, line.getEndPoint().x, line.getEndPoint().y);
	}

	@Override
	public void visit(Circle circle) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawOval(circle.getStartPoint().x, circle.getStartPoint().y, circle.getEndPoint().x,
				circle.getEndPoint().y);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		// filledCircle.repaint();
	}

}
