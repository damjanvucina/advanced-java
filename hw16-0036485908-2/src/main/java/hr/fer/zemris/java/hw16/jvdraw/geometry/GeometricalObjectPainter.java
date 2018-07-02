package hr.fer.zemris.java.hw16.jvdraw.geometry;

import java.awt.BasicStroke;
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
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(2));
		
		g2d.setColor(line.getFgColor());
		g2d.drawLine(line.getStartPoint().x, line.getStartPoint().y, line.getEndPoint().x, line.getEndPoint().y);
	}

	@Override
	public void visit(Circle circle) {
		int radius = circle.calculateRadius();
		
		g2d.setStroke(new BasicStroke(2));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(circle.getFgColor());
		g2d.drawOval(circle.getStartPoint().x - radius, circle.getStartPoint().y - radius, 2 * radius, 2 * radius);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		int radius = filledCircle.calculateRadius();
		
		g2d.setStroke(new BasicStroke(2));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(filledCircle.getFgColor());
		g2d.drawOval(filledCircle.getStartPoint().x - radius, filledCircle.getStartPoint().y - radius, 2 * radius, 2 * radius);
		
		g2d.setColor(filledCircle.getBgColor());
		g2d.fillOval(filledCircle.getStartPoint().x - radius, filledCircle.getStartPoint().y - radius, 2 * radius, 2 * radius);
	}

}
